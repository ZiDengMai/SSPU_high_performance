package com.sspu_consultor.consult_result_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableDiscoveryClient
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class Consultor_result_service_Application {
    public static void main(String[] args){
        SpringApplication.run(Consultor_result_service_Application.class,args);
    }
}
