package service;

import entity.service_nums.Service_nums;

import java.util.List;

public interface Service_numsService {
    public List<Service_nums> selectService_numsByServcie_id(Long service_id);
    public List<Service_nums> selectAllService_nums();
    public int updateService_numsByService_id(Long service_id,Long user_id,Long q_id,int start,List<Integer> ends);
}
