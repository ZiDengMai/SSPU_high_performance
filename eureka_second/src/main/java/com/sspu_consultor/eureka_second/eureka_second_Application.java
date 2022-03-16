package com.sspu_consultor.eureka_second;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaServer
public class eureka_second_Application {

    public static void main(String args[]){
            SpringApplication.run(eureka_second_Application.class,args);
    }
}
