package com.sspu_consultor.consult_result_service.service;


import com.sspu_consultor.consult_result_service.mapper.AnswersMapper;
import entity.answers.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.AnswersService;

import java.util.List;

@Service
public class AnswersServiceImpl implements AnswersService {
    @Autowired
    private AnswersMapper answersMapper;

    @Override
    public int insertNeoAnswer(Long user_id, Long q_id, String ans_content) {
        return answersMapper.insertNeoAnswer(user_id,q_id,ans_content);
    }

    @Override
    @Transactional
    public int updateAnswer(Long user_id, Long q_id, String ans_content) {
        //System.err.println("!!!!!!!!");
        if(answersMapper.findAllAnswersByQ_idUser_id(user_id,q_id)==null){
            return answersMapper.insertNeoAnswer(user_id,q_id,ans_content);
        }else{
            return answersMapper.updateAnswer(user_id,q_id,ans_content);
        }
    }

    @Override
    public List<Answers> findAllAnswersByQ_id(Long q_id) {
        return answersMapper.findAllAnswersByQ_id(q_id);
    }

    @Override
    public List<Answers> findAllAnswersByUser_id(Long user_id) {
        return findAllAnswersByUser_id(user_id);
    }

    @Override
    public Answers findAllAnswersByQ_idUser_id(Long user_id,Long q_id) {
        return answersMapper.findAllAnswersByQ_idUser_id(user_id,q_id);
    }
}
