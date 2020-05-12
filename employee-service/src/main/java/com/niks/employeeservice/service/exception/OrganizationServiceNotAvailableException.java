package com.niks.employeeservice.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class OrganizationServiceNotAvailableException extends ServiceException {

  public OrganizationServiceNotAvailableException(final String errorMessage, final Throwable errorObject) {
    super(errorMessage, errorObject);
  }

  public OrganizationServiceNotAvailableException(final String errorMessage) {
    super(errorMessage);
  }
}
