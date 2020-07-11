package com.niks.oauth2authenticationservice.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RoleNotFundException extends ServiceException {

  public RoleNotFundException(final String errorMessage, final Throwable errorObject) {
    super(errorMessage, errorObject);
  }

  public RoleNotFundException(final String errorMessage) {
    super(errorMessage);
  }
}

