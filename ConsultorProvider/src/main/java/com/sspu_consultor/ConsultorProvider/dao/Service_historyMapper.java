package com.sspu_consultor.ConsultorProvider.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

@Mapper
public interface Service_historyMapper {
    public int insertService_historyNeoHistory(Long his_id, Long user_id, int type, Date time);
    public int deleteService_history();
    public List<Date> selectService_historByUser_id(Long user_id);
}
