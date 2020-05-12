package com.niks.leaveservice.request.leave.application.employee;

import com.niks.leaveservice.constants.ErrorMessageConstants;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeLeaveApplicationUpdateRequest {

  @NotNull(message = ErrorMessageConstants.LEAVE_EMPLOYEE_ID_NOT_NULL)
  private Long employeeId;

  @NotNull(message = ErrorMessageConstants.LEAVE_LEAVE_STATUS_NOT_NULL)
  private Long leaveStatusId;
}
