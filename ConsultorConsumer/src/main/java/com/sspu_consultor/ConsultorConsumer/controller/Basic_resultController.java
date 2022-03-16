package com.sspu_consultor.ConsultorConsumer.controller;

import entity.CommonResult;
import entity.basic_result.Basic_result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping("/basic_result")
public class Basic_resultController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/res_basicByUser_idQ_id")
    @ResponseBody
    public CommonResult<String> res_basicByUser_idQ_id(Long user_id, Long q_id, String res_basic){
        return restTemplate.getForObject("http://Providers/basic_result/res_basicByUser_idQ_id?user_id="+user_id+"&q_id="+q_id+"&res_basic="+res_basic,CommonResult.class);
    }

    @GetMapping("/find_res_basicByUser_idQ_id")
    @ResponseBody
    public CommonResult<List<Basic_result>> find_res_basicByUser_idQ_id(Long user_id, Long q_id){
        return restTemplate.getForObject("http://Providers/basic_result/find_res_basicByUser_idQ_id?user_id="+user_id+"&q_id="+q_id,CommonResult.class);
    }

    @GetMapping("/find_res_basicByUser_id")
    @ResponseBody
    public CommonResult<List<Basic_result>> find_res_basicByUser_id(Long user_id){
        return restTemplate.getForObject("http://Providers/basic_result/find_res_basicByUser_id?user_id="+user_id,CommonResult.class);
    }

    @GetMapping("/find_res_basicByQ_id")
    @ResponseBody
    public CommonResult<List<Basic_result>> find_res_basicByQ_id(Long q_id){
        return restTemplate.getForObject("http://Providers/basic_result/find_res_basicByQ_id?q_id="+q_id,CommonResult.class);
    }

    @GetMapping("/insertRes_basicByUser_idQ_id")
    @ResponseBody
    public CommonResult<String> insertRes_basicByUser_idQ_id(Long user_id,Long q_id,String res_basic){
        return restTemplate.getForObject("http://Providers/basic_result/insertRes_basicByUser_idQ_id?user_id="+user_id+"&q_id="+q_id+"&res_basic="+res_basic,CommonResult.class);
    }
}
