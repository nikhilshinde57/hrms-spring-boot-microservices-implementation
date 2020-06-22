package com.niks.oauth2authenticationservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean()
      throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  @Bean
  public UserDetailsService userDetailsServiceBean() throws Exception {
    return super.userDetailsServiceBean();
  }

  @Override
  protected void configure(
      AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser("john.carnell")
        .password("{noop}password1")
        .roles("USER")
        .and()
        .withUser("william.woodward")
        .password("{noop}password2")
        .roles("USER", "ADMIN");
  }

//  @Autowired
//  private AuthEntryPointJwt unauthorizedHandler;

//  @Override
//  protected void configure(HttpSecurity http) throws Exception {
//
//    http.cors().and().csrf().disable()
//        //.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
//        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//        .authorizeRequests().antMatchers("/api/auth/signup").permitAll()
//        .antMatchers("/api/test/**").permitAll()
//        .anyRequest().authenticated();
//  }

  @Bean
  protected ResourceServerConfigurerAdapter resourceServerConfigurerAdapter() {
    return new ResourceServerConfigurerAdapter() {
      @Override
      public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/api/auth/signup/**").permitAll()
            .anyRequest().authenticated();
      }
    };
  }
}
