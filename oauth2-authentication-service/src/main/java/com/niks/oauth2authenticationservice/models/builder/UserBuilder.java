package com.niks.oauth2authenticationservice.models.builder;

import com.niks.oauth2authenticationservice.models.db.User;
import com.niks.oauth2authenticationservice.request.SignupRequest;
import java.util.UUID;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

  private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

  public User buildFromRequest(SignupRequest signupRequest) {
    User user = new User();
    user.setEmail(signupRequest.getEmail());
    user.setUserName(signupRequest.getUsername());
    user.setGuid(UUID.randomUUID());
    user.setPassword(encoder.encode(signupRequest.getPassword()));
    return user;
  }
}

