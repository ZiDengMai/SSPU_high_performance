package com.sspu_consultor.ConsultorConsumer.controller;

import entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/insertIntoNeoUser")
    @ResponseBody
    public CommonResult insertIntoNeoUser(String user_name, String user_password){
        return restTemplate.getForObject("http://Providers/user/insertIntoNeoUser?user_name="+user_name+"&"+"user_password="+user_password,CommonResult.class);
    }

    @GetMapping("/selectUserByNameAndPassword")
    @ResponseBody
    public CommonResult selectUserByNameAndPassword(String user_name, String user_password){
        return restTemplate.getForObject("http://Providers/user/selectUserByNameAndPassword?user_name="+user_name+"&"+"user_password="+user_password,CommonResult.class);
    }

    @GetMapping("/updateUser_expiredAndUser_testByUser_name")
    @ResponseBody
    public CommonResult<Integer> updateUser_expiredAndUser_testByUser_name(String user_name){
        return restTemplate.getForObject("http://Providers/user/updateUser_expiredAndUser_testByUser_name?user_name="+user_name,CommonResult.class);
    }

    @GetMapping("/updateUser_passwordByUser_name")
    @ResponseBody
    public CommonResult<String> updateUser_passwordByUser_name(String user_name, String user_password,String user_test){
        return restTemplate.getForObject("http://Providers/user/updateUser_passwordByUser_name?user_name="+user_name+"&user_password="+user_password+"&user_test="+user_test,CommonResult.class);
    }

}
