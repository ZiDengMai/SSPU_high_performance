package com.sspu_consultor.ConsultorProvider.controller;

import com.alibaba.fastjson.JSON;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import entity.CommonResult;
import entity.service_nums.Service_nums;
import entity.service_nums.Service_nums_pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import service.Service_numsService;

import java.util.List;

@RestController
@RequestMapping("/service_nums")
public class Service_numsController {
    @Autowired
    private Service_numsService service_numsService;


    @GetMapping("/selectService_numsByServcie_id")
    @ResponseBody
    public CommonResult<List<Service_nums>> selectService_numsByServcie_id(Long service_id){
        List<Service_nums> ans=service_numsService.selectService_numsByServcie_id(service_id);
        return new CommonResult<List<Service_nums>>(200,ans);
    };

    @GetMapping("/selectAllService_nums")
    @ResponseBody
    public CommonResult<String> selectAllService_nums(){
        List<Service_nums> ans=service_numsService.selectAllService_nums();

        return new CommonResult<String>(200, JSON.toJSONString(ans));
    };

    @PostMapping("/updateService_numsByService_id")
    @ResponseBody
    @HystrixCommand(fallbackMethod = "updateService_numse_timeout",commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds",value="3000"),//毫秒级超时
            @HystrixProperty(name="circuitBreaker.enabled",value = "true"),//启用短路器
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value = "10"),//线程数经过这个数字后开始
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value = "10000"),//窗口期，过后尝试恢复服务
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value = "60"),//阀值内达到这个降级次数就熔断
            @HystrixProperty(name = "execution.isolation.semaphore.maxConcurrentRequests", value="300")//该接口可承受并发
            //更多命令请见hystrixCommandProperties
    })
    public CommonResult<String> updateService_numsByService_id(@RequestBody Service_nums_pack service_nums_pack){
       /* boolean tst=true;
        while(tst){
            if(!tst){
                return new CommonResult<String>(200,"更新成功");
            }
        }*/
        if(service_numsService.updateService_numsByService_id(service_nums_pack.getService_id(),service_nums_pack.getUser_id(),service_nums_pack.getQ_id(),service_nums_pack.getStart(),service_nums_pack.getEnds())>0){
            return new CommonResult<String>(200,"更新成功");
        }else{
            return new CommonResult<String>(444,"更新失败");
        }
    }

    public CommonResult<String> updateService_numse_timeout(@RequestBody Service_nums_pack service_nums_pack){
        return new CommonResult<String>(500,"当前人数有点多，请稍后再试一试");
    }
}
