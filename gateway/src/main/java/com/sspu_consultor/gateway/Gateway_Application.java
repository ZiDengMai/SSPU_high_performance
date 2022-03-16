package com.sspu_consultor.gateway;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class Gateway_Application {

    public static void main(String args[]){
       SpringApplication.run(Gateway_Application.class,args);
    }
}
