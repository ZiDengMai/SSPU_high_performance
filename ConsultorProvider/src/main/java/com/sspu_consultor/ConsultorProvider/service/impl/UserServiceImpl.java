package com.sspu_consultor.ConsultorProvider.service.impl;

import cn.hutool.core.util.IdUtil;
import com.sspu_consultor.ConsultorProvider.dao.UserMapper;
import entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import service.UserService;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${init.machineId}")
    private Long machineId;

    @Value("${init.dataCenterId}")
    private Long dataCenterId;

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailUserName;

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

    @Override
    public int updateUser_expiredAndUser_testByUser_name(String user_name) {
        String verifyCode = String.valueOf(new Random().nextInt(899999) + 100000);
        Timestamp outDate = new Timestamp(System.currentTimeMillis() + 5 * 60 * 1000);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<html><head><title></title></head><body>");
        stringBuilder.append("您好<br/>");
        stringBuilder.append("您的验证码是：").append(verifyCode).append("<br/>");
        stringBuilder.append("您可以复制此验证码并返回至XXX，以验证您的邮箱。<br/>");
        stringBuilder.append("此验证码只能使用一次，在5分钟内有效。验证成功则自动失效。<br/>");
        stringBuilder.append("如果您没有进行上述操作，请忽略此邮件。");
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = null;
        try {
            mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(mailUserName);//这里只是设置username 并没有设置host和password，因为host和password在springboot启动创建JavaMailSender实例的时候已经读取了
            mimeMessageHelper.setTo(user_name);
            mimeMessage.setSubject("邮箱验证-XXX");
            mimeMessageHelper.setText(stringBuilder.toString(),true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return userMapper.updateUser_expiredAndUser_testByUser_name(user_name,new Date(outDate.getTime()),verifyCode);
    }

    @Override
    public int updateUser_passwordByUser_name(String user_name, String user_password,String user_test) {
        List<User> ans=userMapper.selectUserByName(user_name);
        if(!ans.isEmpty()){
            User u=ans.get(0);
            Date now=new Date();
            if(now.before(u.getUser_expired()) && user_test.equals(u.getUser_test())){
                return userMapper.updateUser_passwordByUser_name(user_name,user_password);
            }
        }
        return 0;
    }

    @Override
    public String selectUser_testByUser_name(String user_name) {
        return null;
    }


}
