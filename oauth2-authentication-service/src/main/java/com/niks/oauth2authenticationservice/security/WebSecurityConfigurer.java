package com.niks.oauth2authenticationservice.security;

import com.niks.oauth2authenticationservice.service.UserDetailsCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {
  @Bean
  public UserDetailsService userDetailsCustomServiceBean()
      throws Exception {
    return new UserDetailsCustomService();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean()
      throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(
      AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsCustomServiceBean());
//In memory user configuration also we can use. Keeping code just for reference
//    auth.inMemoryAuthentication()
//        .withUser("john.carnell")
//        .password("{noop}password1")
//        .roles("USER")
//        .and()
//        .withUser("william.woodward")
//        .password("{noop}password2")
//        .roles("USER", "ADMIN");
  }

  @Bean
  protected ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
    return new ResourceServerConfigurerAdapter() {
      @Override
      public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/oauth/signup/**").permitAll()
            .antMatchers("/oauth/user/**").permitAll()
            .antMatchers("/webjars/**").permitAll()
            .antMatchers("/actuator/**").permitAll()
            .anyRequest().authenticated();
      }

      @Override
      public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(null);
      }
    };
  }

}
