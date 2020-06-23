package com.niks.oauth2authenticationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableResourceServer
@EnableAuthorizationServer
public class OAuth2AuthenticationServiceApplication {

  public static void main(String [] args){
    SpringApplication.run(OAuth2AuthenticationServiceApplication.class, args);
  }
}
