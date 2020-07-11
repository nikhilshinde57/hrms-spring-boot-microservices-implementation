package com.niks.leaveservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;


@SpringBootApplication
@EnableHystrixDashboard
@EnableEurekaClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableResourceServer
@EnableHystrix
public class LeaveServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LeaveServiceApplication.class, args);
  }
}
