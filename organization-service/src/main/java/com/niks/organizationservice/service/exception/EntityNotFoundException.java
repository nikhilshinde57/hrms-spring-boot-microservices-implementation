package com.niks.organizationservice.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends ServiceException {

  public EntityNotFoundException(final String errorMessage, final Throwable errorObject) {
    super(errorMessage, errorObject);
  }

  public EntityNotFoundException(final String errorMessage) {
    super(errorMessage);
  }
}
