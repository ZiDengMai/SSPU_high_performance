package service;

import entity.answers.Answers;

import java.util.List;

public interface AnswersService {
    public int insertNeoAnswer(Long user_id,Long q_id,String ans_content);

    public int updateAnswer(Long user_id,Long q_id,String ans_content);

    public List<Answers> findAllAnswersByQ_id(Long q_id);

    public List<Answers> findAllAnswersByUser_id(Long user_id);

    public Answers findAllAnswersByQ_idUser_id(Long user_id,Long q_id);
}
