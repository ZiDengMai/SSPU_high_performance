package service;

import entity.question.Question;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface QuestionService {
    public Long insertNeoQuestion(Long user_id);
    public int updateQuestion(Long user_id,Long q_id,String q_content,String q_name,String q_script);
    public List<Question> selectQuestionByQ_id(Long q_id);
    public List<Question> selectQuestionByUser_id(Long user_id);
    public int updateCoursesByQ_id(Long q_id,String courses);
    public int updateC_relationsByQ_id(Long q_id,String c_relations);
    public String selectCoursesByQ_id(Long q_id);
    public String selectC_relationsByQ_id(Long q_id);
    public String deletByQ_id(Long q_id);
    public int updateQ_situationByQ_id(Long q_id);
    public int updateStartsAndEndsByQ_id(Long q_id, int route_start, List<Integer> route_ends);
}
