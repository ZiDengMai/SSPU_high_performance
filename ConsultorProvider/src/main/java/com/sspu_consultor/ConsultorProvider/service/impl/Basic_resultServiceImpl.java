package com.sspu_consultor.ConsultorProvider.service.impl;

import com.sspu_consultor.ConsultorProvider.dao.Basic_resultMapper;
import entity.basic_result.Basic_result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import service.Basic_resultService;

import java.util.List;

@Service
public class Basic_resultServiceImpl implements Basic_resultService {


    @Autowired
    Basic_resultMapper basic_resultMapper;

    @Override
    public int res_basicByUser_idQ_id(Long user_id, Long q_id, String res_basic) {
          return basic_resultMapper.res_basicByUser_idQ_id(user_id,q_id,res_basic);
    }

    @Override
    public List<Basic_result> find_res_basicByUser_idQ_id(Long user_id, Long q_id) {
        return basic_resultMapper.find_res_basicByUser_idQ_id(user_id,q_id);
    }

    @Override
    public List<Basic_result> find_res_basicByUser_id(Long user_id) {
        return basic_resultMapper.find_res_basicByUser_id(user_id);
    }

    @Override
    public List<Basic_result> find_res_basicByQ_id(Long q_id) {
        return basic_resultMapper.find_res_basicByQ_id(q_id);
    }

    @Override
    public int insertRes_basicByUser_idQ_id(Long user_id, Long q_id, String res_basic) {
        return basic_resultMapper.insertRes_basicByUser_idQ_id(user_id,q_id,res_basic);
    }

    @Override
    public int countAllanswersSelection(Long q_id,Long user_id) {
        return 0;
    }

    @Override
    public int insertBasic_relationByUser_idQ_id(Long user, Long q_id, String basic_relation,int start,List<Integer> ends) {
        return 0;
    }

    @Override
    public int updateBasic_relationByUser_idQ_id(Long user, Long q_id,int start,List<Integer> ends) {
        return 0;
    }
}
