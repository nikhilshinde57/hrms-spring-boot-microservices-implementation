package com.niks.leaveservice.request.leave.allocation.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niks.leaveservice.constants.ErrorMessageConstants;
import java.time.LocalDate;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeLeaveAllocationUpdateRequest {

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_EMP_ID_NOT_NULL)
  private Long employeeId;

  @NotNull(message = ErrorMessageConstants.LEAVE_LEAVE_TYPE_ID_NOT_NULL)
  private Long leaveTypeId;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  private Long allottedLeaves;

  private Long carriedLeaves;

  private Long additionalLeaves;

  @AssertTrue(message =
      ErrorMessageConstants.EMPLOYEE_LEAVE_ALLOCATED_START_AND_END_DATE_ARE_REQUIRED)
  private boolean isDatesAreValid() {
    return (endDate != null || startDate == null) && (endDate == null || startDate != null);
  }

  @AssertTrue(message = ErrorMessageConstants.LEAVE_END_DATE_CANNOT_LESS_THAN_START_DATE)
  private boolean isEndDateIsValid() {
    //return (endDate != null && startDate != null && endDate.isBefore(startDate));
    return endDate != null && !endDate.isBefore(startDate);
  }

  @AssertTrue(message =
      ErrorMessageConstants.LEAVE_ALLOCATED_LEAVES_ARE_MORE_THAN_ALLOCATED_TIME_PERIOD)
  private boolean isAllottedLeavesCountValid() {
    return allottedLeaves != null && allottedLeaves >= 0;
  }

  @AssertTrue(message =
      ErrorMessageConstants.LEAVE_CARRIED_LEAVES_ARE_MORE_THAN_ALLOCATED_TIME_PERIOD)
  private boolean isCarriedLeavesCountValid() {
    return carriedLeaves != null && carriedLeaves >= 0;
  }

  @AssertTrue(message =
      ErrorMessageConstants.LEAVE_ADDITIONAL_LEAVES_ARE_MORE_THAN_ALLOCATED_TIME_PERIOD)
  private boolean isAdditionalLeavesCountValid() {
    return additionalLeaves != null && additionalLeaves >= 0;
  }
}
