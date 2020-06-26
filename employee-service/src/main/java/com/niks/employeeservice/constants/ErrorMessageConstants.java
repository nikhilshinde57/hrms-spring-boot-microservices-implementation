package com.niks.employeeservice.constants;

public class ErrorMessageConstants {

  private ErrorMessageConstants() {
  }
  /**
   * Error messages related to Employee entity
   */
  public static final String EMPLOYEE_FIRST_NAME_NOT_BLANK = "Employee first name cannot be blank";
  public static final String EMPLOYEE_FIRST_NAME_NOT_NULL = "Employee first name cannot be null";
  public static final String EMPLOYEE_LAST_NAME_NOT_BLANK = "Employee last name cannot be blank";
  public static final String EMPLOYEE_LAST_NAME_NOT_NULL = "Employee last name cannot be null";
  public static final String EMPLOYEE_MIDDLE_NAME_NOT_BLANK = "Employee middle name cannot be blank";
  public static final String EMPLOYEE_DATE_OF_BIRTH_NOT_BLANK = "Employee date of birth cannot be blank";
  public static final String EMPLOYEE_DATE_OF_BIRTH_NOT_NULL = "Employee date of birth cannot be null";
  public static final String EMPLOYEE_EMAIL_NOT_NULL = "Employee email cannot be null";
  public static final String EMPLOYEE_EMAIL_NOT_BLANK = "Employee email cannot be blank";
  public static final String EMPLOYEE_EMAIL_ID_NOT_PROPER = "Please enter a valid Email ID";
  public static final String EMPLOYEE_DATE_OF_JOINING_NOT_NULL = "Employee date of joining cannot be null";
  public static final String EMPLOYEE_DATE_OF_JOINING_NOT_BLANK = "Employee date of joining cannot be blank";
  public static final String EMPLOYEE_DATE_OF_EXIT_NOT_NULL = "Employee date of exit cannot be null";
  public static final String EMPLOYEE_CONTACT_NOT_NULL = "Employee contact cannot be null";
  public static final String EMPLOYEE_CONTACT_NOT_BLANK = "Employee contact cannot be blank";
  public static final String EMPLOYEE_ADDRESS_NOT_NULL = "Employee address cannot be null";
  public static final String EMPLOYEE_DESIGNATION_NOT_NULL = "Employee designation cannot be null";
  public static final String EMPLOYEE_DESIGNATION_NOT_BLANK = "Employee designation cannot be blank";
  public static final String EMPLOYEE_ID_NOT_NULL = "Employee id cannot be null";
  public static final String EMPLOYEE_EMP_ID_NOT_NULL = "Employee emp_id cannot be null";
  public static final String EMPLOYEE_ORGANIZATION_ID_NOT_NULL = "Employee organization id cannot be null";
  public static final String EMPLOYEE_DUPLICATE_EMAIL = "Employee with given email id or empId already exists";
  public static final String EMPLOYEE_BY_ID_NOT_FOUND = "Employee with given id not found";
  public static final String EMPLOYEE_INVALID_STATAUS = "Employee isActive value is should be true or false";
  /**
   * Employee search related constants
   */
  public static final String EMPLOYEE_INVALID_ORDER_BY_VALUE = "Invalid order by value.";
  public static final String EMPLOYEE_INVALID_INVALID_SORT_BY_COLUMN = "Invalid sort by value.";
  public static final String INVALID_PER_PAGE_VALUE = "Invalid per page value.";
  public static final String INVALID_PAGE_PARAMETER_ERROR = "Invalid page value.";

  /**
   * Error messages related to Organization entity
   */
  public static final String ORGANIZATION_BY_ID_NOT_FOUND = "Organization with given id not found";
  public static final String ORGANIZATION_ID_IS_INVALID = "Provided organizationId is not valid";
  public static final String ORGANIZATION_SERVICE_NOT_AVAILABLE = "Organization Service is not available temporarily, could not create employee, try after some time";
  public static final String ORGANIZATION_SERVICE_ERROR = "Something went wrong with Organization service";

  /**
   * Error messages related to Authentication error
   */

  public static final String OAUTH_NOT_SUPPORTED_OPERATION = "Not supported: read access token";
  public static final String OAUTH_FAILED_TO_FETCH_USER =  "Could not fetch user details";
}