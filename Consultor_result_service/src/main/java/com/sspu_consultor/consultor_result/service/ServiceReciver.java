package com.sspu_consultor.consultor_result.service;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListener(queues = "ServiceDirectQueue")
public class ServiceReciver {

    @RabbitHandler
    public void process(Map message) {
        System.err.println("DirectReceiver消费者收到消息  : " + message.get("type"));
    }
}
