package com.niks.employeeservice.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class OrganizationServiceException extends ServiceException {

  private static final long serialVersionUID = 1L;

  public OrganizationServiceException(final String errorMsg, final Throwable errorObject) {
    super(errorMsg, errorObject);
  }

  public OrganizationServiceException(final String errorMsg) {
    super(errorMsg);
  }
}
