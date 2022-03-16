package com.sspu_consultor.ConsultorProvider.config;

import com.alibaba.fastjson.JSON;
import com.sspu_consultor.ConsultorProvider.dao.QuestionMapper;
import entity.question.Question;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//@Configuration
public class EverydayRabbitListner implements ApplicationListener<ContextRefreshedEvent>, Runnable{

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void run() {
        Date before=new Date();
        Date now=new Date();
        while(true) {
            try {
                Thread.sleep(3600000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            now=new Date();
            long n=now.getTime();
            long b=before.getTime();
            if((n-b)/(1000 * 60 * 60 * 24)>=1) {
                System.err.println("更新记录");
                before=now;
                List<Question> lis_que=questionMapper.selectAll(0,10);
                Date d=new Date();
                for(int i=0;i<lis_que.size();i++){
                    Question que=lis_que.get(i);
                    String messageId = String.valueOf(UUID.randomUUID());
                    String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    Map<String,Object> map=new HashMap<>();
                    map.put("messageId",messageId);
                    map.put("createTime",createTime);
                    map.put("type","service1");
                    map.put("q_id",que.getQ_id());
                    map.put("user_id",que.getUser_id());
                    map.put("start",que.getRoute_start());
                    map.put("ends", que.getRoute_ends());
                    rabbitTemplate.convertAndSend("SlowDirectExchange", "SlowServiceDirectRouting", map);
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        EverydayRabbitListner listener = contextRefreshedEvent.getApplicationContext().getBean(EverydayRabbitListner.class);
        new Thread(this).start();
    }

}
