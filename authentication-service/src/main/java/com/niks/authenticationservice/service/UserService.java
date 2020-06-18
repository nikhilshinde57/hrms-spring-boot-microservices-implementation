package com.niks.authenticationservice.service;

import com.niks.authenticationservice.constants.ErrorMessageConstants;
import com.niks.authenticationservice.enums.ERole;
import com.niks.authenticationservice.model.builder.UserBuilder;
import com.niks.authenticationservice.model.db.Role;
import com.niks.authenticationservice.model.db.User;
import com.niks.authenticationservice.repository.RoleRepository;
import com.niks.authenticationservice.repository.UserRepository;
import com.niks.authenticationservice.request.LoginRequest;
import com.niks.authenticationservice.request.SignupRequest;
import com.niks.authenticationservice.response.JwtResponse;
import com.niks.authenticationservice.response.MessageResponse;
import com.niks.authenticationservice.security.jwt.JwtUtils;
import com.niks.authenticationservice.security.service.UserDetailsImpl;
import com.niks.authenticationservice.service.exception.RoleNotFundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtils jwtUtils;

  @Autowired
  UserRepository userRepository;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  UserBuilder userBuilder;

  public ResponseEntity registerUser(SignupRequest signUpRequest){

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

  public ResponseEntity authenticateUser(LoginRequest loginRequest){

    User user = userRepository.findByUserName(loginRequest.getUsername())
        .orElseThrow(() -> new RoleNotFundException(ErrorMessageConstants.USER_NAME_NOT_FOUND));

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    return ResponseEntity.ok(new JwtResponse(jwt,
        userDetails.getId(),
        userDetails.getUsername(),
        userDetails.getEmail(),
        roles));
  }

  private Set<Role> validateAndGetUserRoles(Set<String> strRoles){
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
