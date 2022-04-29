package com.sspu_consultor.ConsultorProvider.service.impl;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.sspu_consultor.ConsultorProvider.Util.CheckByDFA;
import com.sspu_consultor.ConsultorProvider.dao.QuestionMapper;
import entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import service.QuestionService;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            System.err.println("缓存中不存在");
        }else{
            String que= redisTemplate.opsForValue().get("question_id:"+String.valueOf(q_id));
            List<Question> ans=JSON.parseArray(que,Question.class);
            System.err.println("缓存中存在");
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
        String reg="^\\d{19,19}$";
        Pattern pattern=Pattern.compile(reg);
        Matcher matcher = pattern.matcher(key);
        if(matcher.find()){
            List<Question> ans=null;
            ans=questionMapper.selectQuestionByQ_id(Long.parseLong(key));
            if(ans.isEmpty()){
                ans=questionMapper.selectQuestionByQ_name(key);
            }
            return ans;
        }else{
            return questionMapper.selectQuestionByQ_name(key);
        }
    }

    @Override
    public List<Question> selectQuestionByQ_idWithCache(Long q_id,Long user_id) {
        List<Question> questions=questionMapper.selectQuestionByQ_id(q_id);
        if(!questions.isEmpty()) {
            String stu=String.valueOf(user_id);
            redisTemplate.opsForHash().putIfAbsent("que_cacahe", stu, JSON.toJSONString(questions));
            String ans_que=(String)redisTemplate.opsForHash().get("que_cacahe", stu);
            List<Question> list=JSON.parseArray(ans_que,Question.class);
            for(int i=0;i<list.size();i++) {
               
                if (list.get(i).getQ_id().compareTo(q_id) == 0) {
                    list.remove(i);
                    break;
                }
            }

            if(list.size()>5){
                list.remove(0);
            }
            list.add(questions.get(0));

            redisTemplate.opsForHash().put("que_cacahe", stu, JSON.toJSONString(list));
        }
        return questions;
    }

    @Override
    public List<Question> getMyQuestionCache(Long user_id){
        String stu=String.valueOf(user_id);
        List<Question> ans=new LinkedList<>();
        if(redisTemplate.opsForHash().hasKey("que_cacahe",stu)){
            Object str=redisTemplate.opsForHash().get("que_cacahe",stu);
            if(str!=null){
                List<Question> list=JSON.parseArray((String)str,Question.class);
                for(int i=list.size()-1;i>=0;i--){
                    ans.add(list.get(i));
                }
            }
        }
        return ans;
    }
}
