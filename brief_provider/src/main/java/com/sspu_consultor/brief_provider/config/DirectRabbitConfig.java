package com.sspu_consultor.brief_provider.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author : JCccc
 * @CreateTime : 2019/9/3
 * @Description :
 **/
@Configuration
public class DirectRabbitConfig {

    //队列 起名：TestDirectQueue
    @Bean
    public Queue TestDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        System.err.println("完成队列初始化");
        return new Queue("ServiceDirectQueue",true,false,false);
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    public DirectExchange TestDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        System.err.println("完成交换机初始化");
        return new DirectExchange("ServiceDirectExchange",true,false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding bindingDirect() {
        System.err.println("完成交换机和队列的绑定");
        return BindingBuilder.bind(TestDirectQueue()).to(TestDirectExchange()).with("ServiceDirectRouting");
    }

    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

    @Bean
    public Queue SlowDirectQueue() {
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false
        System.err.println("完成缓慢队列初始化");
        return new Queue("SlowDirectQueue",true,false,false);
    }

    //Direct交换机 起名：TestDirectExchange
    @Bean
    public DirectExchange SlowDirectExchange() {
        //  return new DirectExchange("TestDirectExchange",true,true);
        System.err.println("完成交换机初始化");
        return new DirectExchange("SlowDirectExchange",true,false);
    }

    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
    @Bean
    public Binding slowBindingDirect() {
        System.err.println("完成交换机和队列的绑定");
        return BindingBuilder.bind(SlowDirectQueue()).to(SlowDirectExchange()).with("SlowServiceDirectRouting");
    }

}

