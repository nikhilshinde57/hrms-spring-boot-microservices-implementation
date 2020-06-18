//package com.niks.oauth2authenticationservice.security;
//
//import java.util.Arrays;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenEnhancer;
//import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//
//@Configuration
//public class JWTOAuth2Config extends AuthorizationServerConfigurerAdapter {
//
////  @Autowired
////  private ServiceConfig serviceConfig;
////  @Bean
////  public TokenStore tokenStore() {
////    return new JwtTokenStore(jwtAccessTokenConverte());
////  }
////  @Bean
////  @Primary
////  public DefaultTokenServices tokenServices() {
////    DefaultTokenServices defaultTokenServices
////        = new DefaultTokenServices();
////    defaultTokenServices.setTokenStore(tokenStore());
////    defaultTokenServices.setSupportRefreshToken(true);
////    return defaultTokenServices;
////  }
////
////  @Bean
////  public JwtAccessTokenConverter jwtAccessTokenConverter() {
////    JwtAccessTokenConverter converter =
////        new JwtAccessTokenConverter();
////    converter
////        .setSigningKey(serviceConfig.getJwtSigningKey());
////    return converter;
////  }
////
////  @Bean
////  public TokenEnhancer jwtTokenEnhancer() {
////    return new JWTTokenEnhancer();
////  }
//
//  @Bean
//  public TokenStore tokenStore() {
//    return new InMemoryTokenStore();
//  }
//  @Autowired
//  private AuthenticationManager authenticationManager;
//  @Autowired
//  private UserDetailsService userDetailsService;
//  @Autowired
//  private TokenStore tokenStore;
//  @Autowired
//  private DefaultTokenServices tokenServices;
//  @Autowired
//  private JwtAccessTokenConverter jwtAccessTokenConverter;
//  @Autowired
//  private TokenEnhancer jwtTokenEnhancer;
//
//  @Override
//  public void configure(AuthorizationServerEndpointsConfigurer endpoints)
//      throws Exception {
//    TokenEnhancerChain tokenEnhancerChain =
//        new TokenEnhancerChain();
//    tokenEnhancerChain
//        .setTokenEnhancers(
//            Arrays.asList(
//                jwtTokenEnhancer,
//                jwtAccessTokenConverter));
//    endpoints
//        .tokenStore(tokenStore)
//        .accessTokenConverter(jwtAccessTokenConverter)
//        .authenticationManager(authenticationManager)
//        .userDetailsService(userDetailsService);
//  }
//}
