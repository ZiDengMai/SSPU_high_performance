package com.sspu_consultor.consult_result_service.service;

import cn.hutool.core.util.IdUtil;

import com.sspu_consultor.consult_result_service.mapper.QuestionMapper;
import entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.QuestionService;

import java.util.List;

@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionMapper questionMapper;

    @Value("${init.machineId}")
    private Long machineId;

    @Value("${init.dataCenterId}")
    private Long dataCenterId;

    @Override
    public Long insertNeoQuestion(Long user_id) {
        Long q_id=IdUtil.createSnowflake(machineId,dataCenterId).nextId();
        questionMapper.insertNeoQuestion( user_id,q_id);
        return q_id;
    }

    @Override
    public int updateQuestion(Long user_id, Long q_id,String q_content,String q_name,String q_script) {
        return questionMapper.updateQuestion(user_id,q_id,q_content);
    }

    @Override
    public List<Question> selectQuestionByQ_id(Long q_id) {
        return questionMapper.selectQuestionByQ_id(q_id);
    }

    @Override
    public List<Question> selectQuestionByUser_id(Long user_id) {
        return questionMapper.selectQuestionByUser_id(user_id);
    }

    @Override
    public int updateCoursesByQ_id(Long q_id, String courses) {
        return 0;
    }

    @Override
    public int updateC_relationsByQ_id(Long q_id, String c_relations) {
        return 0;
    }

    @Override
    public String selectCoursesByQ_id(Long q_id) {
        return null;
    }

    @Override
    public String selectC_relationsByQ_id(Long q_id) {
        return null;
    }

    @Override
    public String deletByQ_id(Long q_id) {
        return null;
    }

    @Override
    public int updateQ_situationByQ_id(Long q_id) {
        return 0;
    }

    @Override
    public int updateStartsAndEndsByQ_id(Long q_id, int route_start, List<Integer> route_ends) {
        return 0;
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
