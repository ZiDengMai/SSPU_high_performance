package com.sspu_consultor.ConsultorConsumer.controller;

import com.alibaba.fastjson.JSON;
import entity.CommonResult;
import entity.service_nums.Service_nums;
import entity.service_nums.Service_nums_pack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import service.Service_numsService;

import java.util.List;

@RestController
@RequestMapping("/service_nums")
public class Service_numsController {


    @Autowired
    RestTemplate restTemplate;
    @GetMapping("/selectService_numsByServcie_id")
    @ResponseBody
    public CommonResult<List<Service_nums>> selectService_numsByServcie_id(Long service_id){
        return restTemplate.getForObject("http://Providers/service_nums/selectService_numsByServcie_id?service_id="+service_id, CommonResult.class);
    }

    @GetMapping("/selectAllService_nums")
    @ResponseBody
    public CommonResult<List<Service_nums>> selectAllService_nums(){
        //return restTemplate.getForObject("http://Providers/service_nums/selectAllService_nums", CommonResult.class);
        CommonResult<String> commonResult=restTemplate.getForObject("http://Providers/service_nums/selectAllService_nums", CommonResult.class);
        String ans=commonResult.getData();
        List<Service_nums> list=JSON.parseArray(ans,Service_nums.class);
        return new CommonResult<List<Service_nums>>(200, list);
    }

    @PostMapping("/updateService_numsByService_id")
    @ResponseBody
    public CommonResult<Integer> updateService_numsByService_id(@RequestBody Service_nums_pack service_nums_pack){
        return restTemplate.postForObject("http://Providers/service_nums/updateService_numsByService_id",service_nums_pack,CommonResult.class);
    }
}