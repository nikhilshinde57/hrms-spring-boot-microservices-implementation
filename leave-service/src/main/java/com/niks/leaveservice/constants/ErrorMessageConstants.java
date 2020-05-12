package com.niks.leaveservice.constants;

public class ErrorMessageConstants {

  private ErrorMessageConstants() {
  }

  /**
   * Error messages related to Organization_Leave entity
   */

  public static final String ORGANIZATION_LEAVE_LEAVE_TYPE_ID_NOT_NULL = "LeaveTypeId cannot be null";
  public static final String ORGANIZATION_LEAVE_ORGANIZATION_ID_NOT_NULL = "OrganizationId cannot be null";
  public static final String ORGANIZATION_LEAVE_COUNT_NOT_NULL = "Leave count cannot be null";
  public static final String ORGANIZATION_LEAVE_COUNT_CAN_NOT_BE_LESS_THAN_ONE =
      "Leave count must be greater than zero";
  public static final String ORGANIZATION_LEAVE_WITH_ORGANIZATION_AND_LEAVE_ID_EXISTS =
      "OrganizationLeave record with given organizationId and leaveTypeId is already exits";
  public static final String ORGANIZATION_LEAVE_BY_ID_NOT_NULL = "OrganizationLeave with given Id not found";

  /**
   * Error messages related to Leave_Type entity
   */
  public static final String LEAVE_TYPE_BY_ID_NOT_FOUND = "LeaveType with given id not found";

  /**
   * Error messages related to Organization entity
   */
  public static final String ORGANIZATION_BY_ID_NOT_FOUND = "Organization with given id not found";
  public static final String ORGANIZATION_ID_IS_INVALID = "Provided organizationId is not valid";
  public static final String ORGANIZATION_SERVICE_NOT_AVAILABLE = "Organization Service is not available temporarily, could not create employee, try after some time";
  public static final String ORGANIZATION_SERVICE_ERROR = "Something went wrong with Organization service";
}
