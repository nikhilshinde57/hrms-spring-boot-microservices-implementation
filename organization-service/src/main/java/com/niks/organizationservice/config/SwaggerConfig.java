package com.niks.organizationservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.niks.organizationservice.controller"))
        .build().apiInfo(metaData());
  }

  private ApiInfo metaData() {
    return new ApiInfoBuilder()
        .description("Organization service REST API")
        .title("Organization service REST API")
        .version("1.0")
        .termsOfServiceUrl("Terms of service")
        .contact(new Contact("Nikhil Shinde", "", "nikhilshinde57@gmail.com"))
        .license("Apache License Version 2.0")
        .licenseUrl("https://www.apache.org/licenses/LICENSE-2.0")
        .build();
  }
}
