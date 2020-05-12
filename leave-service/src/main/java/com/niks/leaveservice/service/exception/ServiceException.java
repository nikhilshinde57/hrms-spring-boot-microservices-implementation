package com.niks.leaveservice.service.exception;

public class ServiceException extends RuntimeException {

  public ServiceException(final String errorMessage) {
    super(errorMessage);
  }

  public ServiceException(final String errorMessage, final Throwable errorObject) {
    super(errorMessage, errorObject);
  }
}