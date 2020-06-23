package com.niks.oauth2authenticationservice.service.exception;

import com.niks.oauth2authenticationservice.constants.ErrorMessageConstants;
import org.springframework.security.oauth2.common.exceptions.ClientAuthenticationException;

public class CustomClientRegistrationException extends ClientAuthenticationException {

  public CustomClientRegistrationException(String msg, Throwable t) {
    super(msg, t);
  }

  public CustomClientRegistrationException(String msg) {
    super(msg);
  }
  @Override
  public String getOAuth2ErrorCode() {
    return ErrorMessageConstants.CLIENT_ID_INVALID;
  }
}
