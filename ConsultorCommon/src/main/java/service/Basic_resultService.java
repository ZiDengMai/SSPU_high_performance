package service;

import entity.basic_result.Basic_result;

import java.util.List;

public interface Basic_resultService {
    public int res_basicByUser_idQ_id(Long user_id,Long q_id,String res_basic);
    public List<Basic_result> find_res_basicByUser_idQ_id(Long user_id, Long q_id);
    public List<Basic_result> find_res_basicByUser_id(Long user_id);
    public List<Basic_result> find_res_basicByQ_id(Long q_id);
    public int insertRes_basicByUser_idQ_id(Long user_id,Long q_id,String res_basic);
    public int countAllanswersSelection(Long q_id,Long user_id);
    public int insertBasic_relationByUser_idQ_id(Long user_id,Long q_id,String basic_relation,int start,List<Integer> ends);
    public int updateBasic_relationByUser_idQ_id(Long user_id,Long q_id,int start,List<Integer> ends);
}
