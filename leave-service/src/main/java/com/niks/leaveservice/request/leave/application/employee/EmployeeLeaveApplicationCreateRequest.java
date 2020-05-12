package com.niks.leaveservice.request.leave.application.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.niks.leaveservice.constants.ErrorMessageConstants;
import java.time.LocalDate;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeLeaveApplicationCreateRequest {

  @NotNull(message = ErrorMessageConstants.LEAVE_EMPLOYEE_ID_NOT_NULL)
  private Long employeeId;

  @NotNull(message = ErrorMessageConstants.LEAVE_LEAVE_TYPE_ID_NOT_NULL)
  private Long leaveTypeId;

  @NotNull(message = ErrorMessageConstants.LEAVE_START_DATE_NOT_NULL)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @NotNull(message = ErrorMessageConstants.LEAVE_END_DATE_NOT_NULL)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @AssertTrue(message = ErrorMessageConstants.LEAVE_END_DATE_CANNOT_LESS_THAN_START_DATE)
  private boolean isValidEndDate() {
    return endDate != null && !endDate.isBefore(startDate);
  }
}