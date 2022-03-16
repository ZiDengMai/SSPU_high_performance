package com.sspu_consultor.ConsultorConsumer.controller;

import entity.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("/user")
//@CrossOrigin(origins = "*", maxAge = 3600)
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
}
