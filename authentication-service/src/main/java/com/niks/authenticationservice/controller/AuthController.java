package com.niks.authenticationservice.controller;

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
import com.niks.authenticationservice.service.UserService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  UserService userService;

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
    return userService.authenticateUser(loginRequest);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    return userService.registerUser(signUpRequest);
  }
}