package com.sspu_consultor.ConsultorProvider.controller;

import entity.CommonResult;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @GetMapping("/updateUser_expiredAndUser_testByUser_name")
    public CommonResult<Integer> updateUser_expiredAndUser_testByUser_name(String user_name){
        return new CommonResult<Integer>(200, userService.updateUser_expiredAndUser_testByUser_name(user_name));
    }

    @GetMapping("/updateUser_passwordByUser_name")
    public CommonResult<String> updateUser_passwordByUser_name(String user_name, String user_password,String user_test){
        int ans= userService.updateUser_passwordByUser_name(user_name,user_password,user_test);
        if(ans==0){
            return new CommonResult<String>(500, "验证码不正确或已经过期");
        }else{
            return new CommonResult<String>(200, "更新成功");
        }
    }
}
