package com.niks.oauth2authenticationservice.service;

import com.niks.oauth2authenticationservice.constants.ErrorMessageConstants;
import com.niks.oauth2authenticationservice.models.db.Role;
import com.niks.oauth2authenticationservice.models.db.User;
import com.niks.oauth2authenticationservice.repository.UserRepository;
import com.niks.oauth2authenticationservice.service.exception.RoleNotFundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDetailsCustomService implements UserDetailsService {

  @Autowired
  UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String userName) {

    User user = userRepository.findByUserName(userName)
        .orElseThrow(() -> new RoleNotFundException(ErrorMessageConstants.USER_NAME_NOT_FOUND));
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
    org.springframework.security.core.userdetails.User.UserBuilder builder = org.springframework.security.core.userdetails.User
        .builder().passwordEncoder(encoder::encode);
    if (user != null) {

      builder = org.springframework.security.core.userdetails.User.withUsername(userName);
      builder.password(user.getPassword());
      builder.roles(getRoleNames(user.getRoles()));
    } else {
      throw new UsernameNotFoundException("User not found.");
    }
    return builder.build();
  }

  private String [] getRoleNames(Set<Role> roles){
    List<String> roleList = new ArrayList<>();
    for (Role role:roles) {
      roleList.add(role.getName().name());
    }
    return (String[]) roleList.toArray();
  }
}
