package com.sspu_consultor.ConsultorProvider.config;

import com.sspu_consultor.ConsultorProvider.dao.Service_numsMapper;
import entity.service_nums.Service_nums;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EveryDateUpdateServiceNum extends QuartzJobBean {
    @Autowired
    Service_numsMapper service_numsMapper;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String msg = (String) jobExecutionContext.getJobDetail().getJobDataMap().get("msg");
        System.err.println("current time :"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "---" + msg);
        List<Service_nums> service_list=service_numsMapper.selectAllService_nums();
        for(int i=0;i<service_list.size();i++){
            service_numsMapper.updateService_numsByService_id(service_list.get(i).getService_id(),200);
        }
    }
}
