package com.sspu_consultor.brief_provider.controller;

import entity.CommonResult;
import entity.basic_result.Basic_result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.Basic_resultService;

import java.util.List;

@Controller
@RequestMapping("/basic_result")
@CrossOrigin
public class Basic_resultController {
    @Autowired
    Basic_resultService basic_resultService;

    @GetMapping("/res_basicByUser_idQ_id")
    @ResponseBody
    public CommonResult<String> res_basicByUser_idQ_id(Long user_id, Long q_id, String res_basic){
        if(basic_resultService.res_basicByUser_idQ_id(user_id,q_id,res_basic)>=1){
            return new CommonResult<String>(200,"插入成功");
        }else {
            return new CommonResult<String>( 500, "插入失败");
        }
    }

    @GetMapping("/find_res_basicByUser_idQ_id")
    @ResponseBody
    public CommonResult<List<Basic_result>> find_res_basicByUser_idQ_id(Long user_id, Long q_id){
        return new CommonResult<List<Basic_result>>(200,basic_resultService.find_res_basicByUser_idQ_id(user_id,q_id));
    }

    @GetMapping("/find_res_basicByUser_id")
    @ResponseBody
    public CommonResult<List<Basic_result>> find_res_basicByUser_id(Long user_id){
        return new CommonResult<List<Basic_result>>(200,basic_resultService.find_res_basicByUser_id(user_id));
    }

    @GetMapping("/find_res_basicByQ_id")
    @ResponseBody
    public CommonResult<List<Basic_result>> find_res_basicByQ_id(Long q_id){
        return new CommonResult<List<Basic_result>>(200,basic_resultService.find_res_basicByQ_id(q_id));

    }

    @GetMapping("/insertRes_basicByUser_idQ_id")
    @ResponseBody
    public CommonResult<String> insertRes_basicByUser_idQ_id(Long user_id,Long q_id,String res_basic){
        if(basic_resultService.insertRes_basicByUser_idQ_id(user_id,q_id,res_basic)>=1){
            return new CommonResult<String>(200,"插入成功");
        }else {
            return new CommonResult<String>( 500, "插入失败");
        }
    }
}
