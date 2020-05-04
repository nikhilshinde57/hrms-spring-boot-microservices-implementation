package com.niks.leaveservice;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LeaveServiceApplication {

  public static void main(String[] args) {
    SpringApplication.run(LeaveServiceApplication.class, args);
  }
}
