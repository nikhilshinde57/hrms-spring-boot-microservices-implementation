package com.niks.oauth2authenticationservice.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EntityAlreadyExistsException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public EntityAlreadyExistsException(final String errorMsg, final Throwable errorObject) {
    super(errorMsg, errorObject);
  }

  public EntityAlreadyExistsException(final String errorMsg) {
    super(errorMsg);
  }
}
