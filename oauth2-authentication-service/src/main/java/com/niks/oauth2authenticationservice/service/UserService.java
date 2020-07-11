package com.niks.oauth2authenticationservice.service;

import com.niks.oauth2authenticationservice.constants.ErrorMessageConstants;
import com.niks.oauth2authenticationservice.enums.ERole;
import com.niks.oauth2authenticationservice.models.builder.UserBuilder;
import com.niks.oauth2authenticationservice.models.db.Role;
import com.niks.oauth2authenticationservice.models.db.User;
import com.niks.oauth2authenticationservice.repository.RoleRepository;
import com.niks.oauth2authenticationservice.repository.UserRepository;
import com.niks.oauth2authenticationservice.request.SignupRequest;
import com.niks.oauth2authenticationservice.service.exception.EntityAlreadyExistsException;
import com.niks.oauth2authenticationservice.service.exception.RoleNotFundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  UserBuilder userBuilder;

  //Exception handling is missing
  public boolean registerUser(SignupRequest signUpRequest) {

    if (userRepository.existsByUserName(signUpRequest.getUsername())) {
      throw new EntityAlreadyExistsException(ErrorMessageConstants.USER_NAME_ALREADY_IN_USE);
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new EntityAlreadyExistsException(ErrorMessageConstants.USER_EMAIL_ALREADY_IN_USE);
    }

    // Create new user's account
    User user = userBuilder.buildFromRequest(signUpRequest);
    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = validateAndGetUserRoles(strRoles);

    user.setRoles(roles);
    userRepository.save(user);
    //Service dose not return the response entity
    return true;
  }

  public Map<String, Object> getUserInfo(OAuth2Authentication user) {

    Map<String, Object> userInfo = new HashMap<>();
    userInfo.put(
        "user",
        user.getUserAuthentication()
            .getPrincipal());
    userInfo.put(
        "authorities",
        AuthorityUtils.authorityListToSet(
            user.getUserAuthentication()
                .getAuthorities()));
    userInfo.put("clientId", user.getOAuth2Request()
        .getClientId());
    userInfo.put("scope", user.getOAuth2Request()
        .getScope());
    return userInfo;
  }

  private Set<Role> validateAndGetUserRoles(Set<String> strRoles) {
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.USER)
          .orElseThrow(() -> new RuntimeException(ErrorMessageConstants.ROLE_NOT_FOUND));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role.toLowerCase()) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ADMIN)
                .orElseThrow(() -> new RoleNotFundException(ErrorMessageConstants.ROLE_NOT_FOUND));
            roles.add(adminRole);

            break;
          case "tl":
            Role modRole = roleRepository.findByName(ERole.TL)
                .orElseThrow(() -> new RoleNotFundException(ErrorMessageConstants.ROLE_NOT_FOUND));
            roles.add(modRole);

            break;
          default:
            Role userRole = roleRepository.findByName(ERole.USER)
                .orElseThrow(() -> new RoleNotFundException(ErrorMessageConstants.ROLE_NOT_FOUND));
            roles.add(userRole);
        }
      });
    }
    return roles;
  }
}

