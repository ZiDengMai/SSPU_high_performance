package com.sspu_consultor.ConsultorProvider.controller;

import entity.CommonResult;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/insertIntoNeoUser")
    public CommonResult insertIntoNeoUser(String user_name, String user_password){
        int row=userService.insertIntoNeoUser(user_name,user_password);
        if(row==0){
            return new CommonResult<Integer>(500,row);
        }else{
            return new CommonResult<Integer>(200,row);
        }
    }

    @GetMapping("/selectUserByNameAndPassword")
    public CommonResult selectUserByNameAndPassword(String user_name, String user_password){
        User user=userService.selectUserByNameAndPassword(user_name,user_password);
        if(user!=null){
            return new CommonResult<User>(200,user);
        }
        return new CommonResult<User>(500,null);
    }
}
