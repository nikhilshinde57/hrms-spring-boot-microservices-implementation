package com.niks.employeeservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Config class for creation of any ad hoc spring beans
 */
@Configuration
public class SpringBeanConfig {

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

}
