package com.sspu_consultor.brief_provider.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;

import com.sspu_consultor.brief_provider.dao.Service_historyMapper;
import com.sspu_consultor.brief_provider.dao.Service_numsMapper;
import entity.service_nums.Service_nums;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import service.Service_numsService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
public class Service_numsServiceImpl implements Service_numsService {
    @Autowired
    private Service_numsMapper service_numsMapper;

    @Autowired
    private Service_historyMapper service_historyMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;



    @Value("${init.machineId}")
    private Long machineId;

    @Value("${init.dataCenterId}")
    private Long dataCenterId;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public List<Service_nums> selectService_numsByServcie_id(Long service_id) {
        return service_numsMapper.selectService_numsByServcie_id(service_id);
    }

    @Override
    public List<Service_nums> selectAllService_nums() {
        return  service_numsMapper.selectAllService_nums();
    }

    @Override
    public int updateService_numsByService_id(Long service_id,Long user_id,Long q_id,int start,List<Integer> ends) {
        String lockName="lock_serive_id: "+service_id;
        String uuid=IdUtil.simpleUUID()+Thread.currentThread().getName();
        int ans=0;
        //单位毫秒,1000毫秒等于1秒
        if(aquire_lock_for_update(lockName,uuid,10000,30000)){
            try{
                Service_nums service_nums=service_numsMapper.selectService_numsByServcie_id(service_id).get(0);
                int nums=service_nums.getNums();
                if(nums>0){
                    Long his_id=IdUtil.createSnowflake(machineId,dataCenterId).nextId();
                    ans=service_numsMapper.updateService_numsByService_id(service_id,nums-1);
                    int type=service_id.intValue();
                    service_historyMapper.insertService_historyNeoHistory(his_id,user_id,type,new Date());
                    String messageId = String.valueOf(UUID.randomUUID());
                    String messageData = "for" + service_nums.getScript();
                    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    Map<String,Object> map=new HashMap<>();
                    map.put("messageId",messageId);
                    map.put("messageData",messageData);
                    map.put("createTime",createTime);
                    map.put("type","service"+service_id);
                    map.put("q_id",q_id);
                    map.put("user_id",user_id);
                    map.put("start",start);
                    map.put("ends", JSON.toJSONString(ends));
                    rabbitTemplate.convertAndSend("SlowDirectExchange", "SlowServiceDirectRouting", map);
                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(!release_lock(lockName,uuid)){
                    System.err.println("锁释放异常！！！！！");
                }
                return ans;
            }
        }
        return 0;
        //return service_numsMapper.updateService_numsByService_id(service_id,nums);
    }

    // locktime是redis的过期时间，timeout_misec尝试获取锁的最长时间
    public boolean aquire_lock_for_update(String lockName,String uuid,int locktime,int timeout_misec){
        Calendar oldDate=Calendar.getInstance();
        oldDate.setTime(new Date());
        Long timeOld=oldDate.getTimeInMillis();
        while((new Date().getTime())-timeOld<=timeout_misec){
            if(redisTemplate.opsForValue().setIfAbsent( lockName, uuid, locktime, TimeUnit.MILLISECONDS)){
                return true;
            }
        }
        return false;
    }

    public boolean release_lock(String lockName,String uuid){
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.watch(lockName);
        try{
            if(redisTemplate.opsForValue().get(lockName).equals(uuid)){
                redisTemplate.multi();
                redisTemplate.delete(lockName);
                redisTemplate.exec();
            }
            redisTemplate.unwatch();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
