package com.niks.leaveservice.request.leave.allocation.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niks.leaveservice.constants.ErrorMessageConstants;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeLeaveAllocationCreateRequest {

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_EMP_ID_NOT_NULL)
  private Long employeeId;

  @NotNull(message = ErrorMessageConstants.LEAVE_LEAVE_TYPE_ID_NOT_NULL)
  private Long leaveTypeId;

  @NotNull(message = ErrorMessageConstants.LEAVE_START_DATE_NOT_NULL)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @NotNull(message = ErrorMessageConstants.LEAVE_END_DATE_NOT_NULL)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_LEAVE_ALLOTTED_LEAVES_NOT_NULL)
  private Long allottedLeaves;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_LEAVE_CARRIED_LEAVES_NOT_NULL)
  private Long carriedLeaves;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_LEAVE_ADDITIONAL_LEAVES_NOT_NULL)
  private Long additionalLeaves;

  @AssertTrue(message = ErrorMessageConstants.LEAVE_END_DATE_CANNOT_LESS_THAN_START_DATE)
  private boolean isValidEndDate() {
    return endDate != null && !endDate.isBefore(startDate);
  }

  @AssertTrue(message =
      ErrorMessageConstants.LEAVE_ALLOCATED_LEAVES_ARE_MORE_THAN_ALLOCATED_TIME_PERIOD)
  private boolean isAllottedLeavesCountValid() {
    return !(allottedLeaves != null && allottedLeaves < 0
        && ChronoUnit.DAYS.between(startDate, endDate) + 1 < allottedLeaves);
  }

  @AssertTrue(message =
      ErrorMessageConstants.LEAVE_CARRIED_LEAVES_ARE_MORE_THAN_ALLOCATED_TIME_PERIOD)
  private boolean isCarriedLeavesCountValid() {
    return !(carriedLeaves != null && carriedLeaves < 0
        && ChronoUnit.DAYS.between(startDate, endDate) + 1 < carriedLeaves);
  }

  @AssertTrue(message =
      ErrorMessageConstants.LEAVE_ADDITIONAL_LEAVES_ARE_MORE_THAN_ALLOCATED_TIME_PERIOD)
  private boolean isAdditionalLeavesCountValid() {
    return !(additionalLeaves != null && additionalLeaves < 0
        && ChronoUnit.DAYS.between(startDate, endDate) + 1 < additionalLeaves);
  }
}
