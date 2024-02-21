package com.sspu_consultor.ConsultorProvider.config;

import com.alibaba.fastjson.JSON;
import com.sspu_consultor.ConsultorProvider.dao.QuestionMapper;
import entity.question.Question;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EverydayRabbitListner extends QuartzJobBean {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.err.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
        List<Question> lis_que=questionMapper.selectAll(0,10);
        Date d=new Date();
        for(int i=0;i<lis_que.size();i++){
            Question que=lis_que.get(i);
            String messageId = String.valueOf(UUID.randomUUID());
            String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            Map<String,Object> map=new HashMap<>();
            map.put("messageId",messageId);
            map.put("createTime",createTime);
            map.put("type","service1");
            map.put("q_id",que.getQ_id());
            map.put("user_id",que.getUser_id());
            map.put("start",que.getRoute_start());
            map.put("ends", que.getRoute_ends());
            //延迟10s发送，信息间隔10s
            rabbitTemplate.convertAndSend("SlowDirectExchange", "SlowServiceDirectRouting", map,message->{
                message.getMessageProperties().setExpiration("10000");
                return message;
            });
        }
    }


}
