package com.sspu_consultor.brief_provider.dao;

import org.apache.ibatis.annotations.Mapper;

import java.util.Date;

@Mapper
public interface Service_historyMapper {
    public int insertService_historyNeoHistory(Long his_id, Long user_id, int type, Date time);
    public int deleteService_history();
}
