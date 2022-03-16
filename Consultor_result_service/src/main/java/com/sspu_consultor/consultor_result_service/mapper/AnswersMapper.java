package com.sspu_consultor.consultor_result_service.mapper;

import entity.answers.Answers;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AnswersMapper {

    public int insertNeoAnswer(Long user_id,Long q_id,String ans_content);

    public int updateAnswer(Long user_id,Long q_id,String ans_content);

    public List<Answers> findAllAnswersByQ_id(Long q_id);

    public List<Answers> findAllAnswersByUser_id(Long user_id);

    public Answers findAllAnswersByQ_idUser_id(Long user_id,Long q_id);
}
