package com.sspu_consultor.consult_result_service.mapper;

import entity.question.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QuestionMapper {
    public int insertNeoQuestion(@Param("user_id")Long user_id,@Param("q_id")Long q_id);
    public int updateQuestion(@Param("user_id")Long user_id,@Param("q_id")Long q_id,@Param("q_content")String q_content);
    public List<Question> selectQuestionByQ_id(@Param("q_id")Long q_id);
    public List<Question> selectQuestionByUser_id(@Param("user_id")Long user_id);
}
