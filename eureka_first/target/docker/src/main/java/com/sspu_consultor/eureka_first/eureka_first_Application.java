package com.sspu_consultor.eureka_first;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableDiscoveryClient
@EnableEurekaServer
public class eureka_first_Application {
    public static void main(String args[]){
        SpringApplication.run(eureka_first_Application.class,args);
    }
}
