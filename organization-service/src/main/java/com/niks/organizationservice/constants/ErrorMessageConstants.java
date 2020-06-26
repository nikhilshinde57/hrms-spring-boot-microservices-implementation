package com.niks.organizationservice.constants;

public class ErrorMessageConstants {

  private ErrorMessageConstants() {
  }
  /**
   * Error messages related to Organization entity
   */
  public static final String ORGANIZATION_NAME_NOT_BLANK = "Organization name cannot be blank";
  public static final String ORGANIZATION_NAME_NOT_NULL = "Organization name cannot be null";
  public static final String ORGANIZATION_ID_NOT_NULL = "Organization id cannot be null";
  public static final String ORGANIZATION_DUPLICATE_NAME = "Organization with given name already "
      + "exists";
  public static final String ORGANIZATION_BY_ID_NOT_FOUND = "Organization with given id not found";
  public static final String ORGANIZATION_INVALID_STATUS = "Organization isActive value is "
      + "should be true or false";

  /**
   * Error messages related to Authentication error
   */

  public static final String OAUTH_NOT_SUPPORTED_OPERATION = "Not supported: read access token";
  public static final String OAUTH_FAILED_TO_FETCH_USER =  "Could not fetch user details";
}
