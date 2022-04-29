package com.sspu_consultor.ConsultorProvider.dao;

import entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMapper {
    public int insertIntoNeoUser(@Param("user_id") Long user_id,@Param("user_name") String user_name,@Param("user_password") String user_password);

    public User selectUserByNameAndPassword(@Param("user_name")String user_name,@Param("user_password") String user_password);

    public List<User> selectUserByName(@Param("user_name")String user_name);

    public int updateUser_expiredAndUser_testByUser_name(@Param("user_name") String user_name, @Param("user_expired") Date user_expired,@Param("user_test") String user_test);

    public int updateUser_passwordByUser_name(@Param("user_name") String user_name,@Param("user_password") String user_password);

    public String selectUser_testByUser_name(@Param("user_name") String user_name);

}
