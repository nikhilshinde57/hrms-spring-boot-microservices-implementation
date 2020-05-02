package com.niks.organizationservice.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception {

  public BadRequestException(final String errorMessage, final Throwable cause) {
    super(errorMessage, cause);
  }

  public BadRequestException(final String errorMessage) {
    super(errorMessage);
  }
}
