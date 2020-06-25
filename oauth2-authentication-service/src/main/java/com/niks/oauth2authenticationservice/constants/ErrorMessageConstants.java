package com.niks.oauth2authenticationservice.constants;

public class ErrorMessageConstants {

  private ErrorMessageConstants() {
  }
  /**
   * Error messages related to User entity
   */
  public static final String USER_NAME_ALREADY_IN_USE = "Username is already taken!";
  public static final String USER_EMAIL_ALREADY_IN_USE = "Email is already in use!";
  public static final String USER_NAME_NOT_FOUND = "User with given Username is not found.";

  /**
   * Error messages related to Role entity
   */
  public static final String ROLE_NOT_FOUND ="Role with given name not found";

  /**
   * Error messages related to ClientRegistrationException
   */
  public static final String CLIENT_ID_INVALID ="invalid_clientId";
  public static final String CLIENT_NOT_REGISTERED ="Client with given clientId/Name/userName not found";
}
