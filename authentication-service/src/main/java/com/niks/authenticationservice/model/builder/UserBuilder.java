package com.niks.authenticationservice.model.builder;

import com.niks.authenticationservice.model.db.User;
import com.niks.authenticationservice.request.SignupRequest;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

  @Autowired
  PasswordEncoder encoder;

  public User buildFromRequest(SignupRequest signupRequest) {
    User user = new User();
    user.setEmail(signupRequest.getEmail());
    user.setUserName(signupRequest.getUsername());
    user.setGuid(UUID.randomUUID());
    user.setPassword(encoder.encode(signupRequest.getPassword()));
    return user;
  }
}
