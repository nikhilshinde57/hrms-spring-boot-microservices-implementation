package com.niks.oauth2authenticationservice.service;

import com.niks.oauth2authenticationservice.constants.ErrorMessageConstants;
import com.niks.oauth2authenticationservice.enums.ERole;
import com.niks.oauth2authenticationservice.models.builder.UserBuilder;
import com.niks.oauth2authenticationservice.models.db.Role;
import com.niks.oauth2authenticationservice.models.db.User;
import com.niks.oauth2authenticationservice.repository.RoleRepository;
import com.niks.oauth2authenticationservice.repository.UserRepository;
import com.niks.oauth2authenticationservice.request.SignupRequest;
import com.niks.oauth2authenticationservice.response.MessageResponse;
import com.niks.oauth2authenticationservice.service.exception.RoleNotFundException;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  UserBuilder userBuilder;

  public ResponseEntity registerUser(SignupRequest signUpRequest) {

    if (userRepository.existsByUserName(signUpRequest.getUsername())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse(ErrorMessageConstants.USER_NAME_ALREADY_IN_USE));
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
          .badRequest()
          .body(new MessageResponse(ErrorMessageConstants.USER_EMAIL_ALREADY_IN_USE));
    }

    // Create new user's account
    User user = userBuilder.buildFromRequest(signUpRequest);

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = validateAndGetUserRoles(strRoles);

    user.setRoles(roles);
    userRepository.save(user);
    return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
  }

  private Set<Role> validateAndGetUserRoles(Set<String> strRoles) {
    Set<Role> roles = new HashSet<>();
    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.USER)
          .orElseThrow(() -> new RuntimeException(ErrorMessageConstants.ROLE_NOT_FOUND));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
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

