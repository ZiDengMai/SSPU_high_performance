package com.sspu_consultor.ConsultorProvider.dao;

import entity.question.Question;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface QuestionMapper {
    public int insertNeoQuestion(@Param("user_id")Long user_id, @Param("q_id")Long q_id, @Param("q_start_time") Date q_start_time, @Param("q_end_time")Date q_end_time,int route_start,String route_ends);
    public int updateQuestion(@Param("user_id")Long user_id,@Param("q_id")Long q_id,@Param("q_content")String q_content,@Param("q_name")String q_name,@Param("q_script")String q_script);
    public List<Question> selectQuestionByQ_id(@Param("q_id")Long q_id);
    public List<Question> selectQuestionByUser_id(@Param("user_id")Long user_id);
    public int updateCoursesByQ_id(Long q_id,String courses);
    public int updateC_relationsByQ_id(Long q_id,String c_relations);
    public String selectCoursesByQ_id(Long q_id);
    public String selectC_relationsByQ_id(Long q_id);
    public String deletByQ_id(Long q_id);
    public List<Question> selectAll(int start,int end);
    public int updateQ_situationByQ_id(Long q_id,int q_situation);
    public int updateStartsAndEndsByQ_id(Long q_id, int route_start, String route_ends);
    public List<Question> selectQuestionByQ_name(String q_name);
}
