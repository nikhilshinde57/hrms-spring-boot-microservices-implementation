package com.niks.leaveservice.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class InsufficientLeaveException extends ServiceException {

  public InsufficientLeaveException(final String errorMessage, final Throwable errorObject) {
    super(errorMessage, errorObject);
  }

  public InsufficientLeaveException(final String errorMessage) {
    super(errorMessage);
  }
}