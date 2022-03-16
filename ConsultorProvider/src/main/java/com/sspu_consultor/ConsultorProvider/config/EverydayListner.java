package com.sspu_consultor.ConsultorProvider.config;

import com.sspu_consultor.ConsultorProvider.dao.QuestionMapper;
import entity.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.Date;
import java.util.List;


@Configuration
public class EverydayListner implements ApplicationListener<ContextRefreshedEvent>, Runnable {

    @Autowired
    QuestionMapper questionMapper;

    @Override
    public void run() {
        Date before=new Date();
        Date now=new Date();
        while(true) {
            try {
                Thread.sleep(10000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            now=new Date();
            long n=now.getTime();
            long b=before.getTime();
            if((n-b)/(1000 * 60 * 60 * 24)>=1) {
                System.err.println("更新记录");
                before=now;
                List<Question> lis_que=questionMapper.selectAll(0,1000);
                Date d=new Date();
                for(int i=0;i<lis_que.size();i++){
                    Question que=lis_que.get(i);
                    if(que.getQ_end_time().getTime()<d.getTime()){
                        questionMapper.deletByQ_id(que.getQ_id());
                    }
                }
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        EverydayListner listener = contextRefreshedEvent.getApplicationContext().getBean(EverydayListner.class);
        new Thread(this).start();
    }
}