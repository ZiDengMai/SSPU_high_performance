package com.sspu_consultor.brief_provider.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;

import com.sspu_consultor.brief_provider.dao.QuestionMapper;
import entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import service.QuestionService;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Value("${init.machineId}")
    private Long machineId;

    @Value("${init.dataCenterId}")
    private Long dataCenterId;

    @Override
    public Long insertNeoQuestion(Long user_id) {
        Long q_id=IdUtil.createSnowflake(machineId,dataCenterId).nextId();
        Date now=new Date();
        Calendar thisDay = Calendar.getInstance();
        thisDay.add(Calendar.DAY_OF_MONTH, 18 * 30);
        Date end=thisDay.getTime();
        if(questionMapper.insertNeoQuestion( user_id, q_id, now, end, 1, "[1]")>=1){
            return q_id;
        }else{
            return (long)0;
        }
    }

    @Override
    public int updateQuestion(Long user_id, Long q_id,String q_content,String q_name,String q_script) {
        return questionMapper.updateQuestion(user_id,q_id,q_content,q_name,q_script);
    }

    @Override
    public List<Question> selectQuestionByQ_id(Long q_id){
        /*redisTemplate.opsForValue().set("myKey","myValue");
        System.out.println(redisTemplate.opsForValue().get("myKey"));*/
        if(redisTemplate.opsForValue().get(String.valueOf(q_id))==null){
            List<Question> ans=questionMapper.selectQuestionByQ_id(q_id);
            String que= JSON.toJSONString(ans);
            redisTemplate.opsForValue().set("question_id:"+String.valueOf(q_id), que,60, TimeUnit.SECONDS);
        }else{
            String que= redisTemplate.opsForValue().get("question_id:"+String.valueOf(q_id));
            List<Question> ans=JSON.parseArray(que,Question.class);
           // String que=redisTemplate.opsForValue().get(String.valueOf(q_id));
            //System.err.println(que);
            return ans;
        }
        return questionMapper.selectQuestionByQ_id(q_id);
    }

    @Override
    public List<Question> selectQuestionByUser_id(Long user_id) {
        return questionMapper.selectQuestionByUser_id(user_id);
    }

    @Override
    public int updateCoursesByQ_id(Long q_id, String courses) {
        return questionMapper.updateCoursesByQ_id(q_id,courses);
    }

    @Override
    public int updateC_relationsByQ_id(Long q_id, String c_relations) {
        return questionMapper.updateC_relationsByQ_id(q_id,c_relations);
    }


    @Override
    public String selectCoursesByQ_id(Long q_id) {
        return questionMapper.selectCoursesByQ_id(q_id);
    }

    @Override
    public String selectC_relationsByQ_id(Long q_id) {
        return questionMapper.selectC_relationsByQ_id(q_id);
    }

    @Override
    public String deletByQ_id(Long q_id) {
        return questionMapper.deletByQ_id(q_id);
    }

    @Override
    public int updateQ_situationByQ_id(Long q_id) {
        return questionMapper.updateQ_situationByQ_id(q_id,1);
    }

    @Override
    public int updateStartsAndEndsByQ_id(Long q_id, int route_start, List<Integer> route_ends) {
        String ends=JSON.toJSONString(route_ends);
        return questionMapper.updateStartsAndEndsByQ_id(q_id,route_start,ends);
    }

    @Override
    public List<Question> selectQuestionsByQ_idOrQ_Name(String key) {
        return null;
    }

    @Override
    public List<Question> selectQuestionByQ_idWithCache(Long q_id,Long user_id) {
        return null;
    }

    @Override
    public List<Question> getMyQuestionCache(Long user_id) {
        return null;
    }
}
