package service;


import entity.user.User;

import java.util.Date;

public interface UserService {
      public int insertIntoNeoUser(String user_name,String user_password);
      public User selectUserByNameAndPassword(String user_name,String user_password);
      public int updateUser_expiredAndUser_testByUser_name(String user_name);
      public int updateUser_passwordByUser_name(String user_name,String user_password,String user_test);
      public String selectUser_testByUser_name(String user_name);
}
