package service;


import entity.user.User;

public interface UserService {
      public int insertIntoNeoUser(String user_name,String user_password);
      public User selectUserByNameAndPassword(String user_name,String user_password);
}
