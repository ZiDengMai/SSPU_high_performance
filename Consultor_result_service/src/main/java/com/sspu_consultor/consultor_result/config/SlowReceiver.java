package com.sspu_consultor.consultor_result.config;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.Basic_resultService;

import java.util.List;
import java.util.Map;

@Component
@RabbitListener(queues = "SlowDirectQueue")
public class SlowReceiver {
    @Autowired
    Basic_resultService basic_resultService;

    @RabbitHandler
    public void process(Map message) {
        System.err.println("DirectReceiver消费者收到消息  : " + message.toString());
        System.err.println("采用了服务一"+message.get("q_id"));
        Long q_id=(Long) message.get("q_id");
        Long user_id=(Long) message.get("user_id");
        int start=(Integer) message.get("start");
        List<Integer> ends= JSON.parseArray((String)message.get("ends"),Integer.class);
        basic_resultService.countAllanswersSelection(q_id,user_id);
        basic_resultService.updateBasic_relationByUser_idQ_id(user_id,q_id,start,ends);
    }
}
