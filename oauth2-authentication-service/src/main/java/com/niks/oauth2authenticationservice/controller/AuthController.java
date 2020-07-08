package com.niks.oauth2authenticationservice.controller;

import com.niks.oauth2authenticationservice.request.SignupRequest;
import com.niks.oauth2authenticationservice.response.MessageResponse;
import com.niks.oauth2authenticationservice.service.UserService;
import com.niks.oauth2authenticationservice.service.exception.EntityAlreadyExistsException;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/oauth/")
public class AuthController {

  @Autowired
  UserService userService;

  @PostMapping("/signup")
  public ResponseEntity registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    try {
      userService.registerUser(signUpRequest);
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
    catch (EntityAlreadyExistsException ex){
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
    catch (Exception ex){
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
  }

  @GetMapping(value = {"/user"}, produces = "application/json")
  public Map<String, Object> user(OAuth2Authentication user) {
    return userService.getUserInfo(user);
  }
}