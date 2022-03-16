package com.sspu_consultor.ConsultorProvider.service.impl;

import cn.hutool.core.util.IdUtil;
import com.sspu_consultor.ConsultorProvider.dao.UserMapper;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${init.machineId}")
    private Long machineId;

    @Value("${init.dataCenterId}")
    private Long dataCenterId;

    @Override
    public int insertIntoNeoUser(String user_name, String user_password) {
        if(userMapper.selectUserByName(user_name).size()==0) {
            return userMapper.insertIntoNeoUser(IdUtil.createSnowflake(machineId,dataCenterId).nextId(),user_name,user_password);
        }else{
            return 0;
        }
    }

    @Override
    public User selectUserByNameAndPassword(String user_name, String user_password) {
        return userMapper.selectUserByNameAndPassword(user_name,user_password);
    }
}
