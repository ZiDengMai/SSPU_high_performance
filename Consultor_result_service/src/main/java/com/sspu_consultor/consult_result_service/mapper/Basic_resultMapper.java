package com.sspu_consultor.consult_result_service.mapper;

import entity.basic_result.Basic_result;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Basic_resultMapper {
    public int res_basicByUser_idQ_id(Long user_id,Long q_id,String res_basic);
    public List<Basic_result> find_res_basicByUser_idQ_id(Long user_id, Long q_id);
    public List<Basic_result> find_res_basicByUser_id(Long user_id);
    public List<Basic_result> find_res_basicByQ_id(Long q_id);
    public int insertRes_basicByUser_idQ_id(Long user_id,Long q_id,String res_basic);
    public int insertBasic_relationByUser_idQ_id(Long user_id,Long q_id,String basic_relation);
    public int updateBasic_relationByUser_idQ_id(Long user_id,Long q_id,String basic_relation);
}
