package com.sspu_consultor.ConsultorProvider.dao;

import entity.service_nums.Service_nums;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Service_numsMapper {
    public List<Service_nums> selectService_numsByServcie_id(Long service_id);
    public List<Service_nums> selectAllService_nums();
    public int updateService_numsByService_id(Long service_id,int nums);
}
