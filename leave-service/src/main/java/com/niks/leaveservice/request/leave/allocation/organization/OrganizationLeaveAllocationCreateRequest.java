package com.niks.leaveservice.request.leave.allocation.organization;

import com.niks.leaveservice.constants.ErrorMessageConstants;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrganizationLeaveAllocationCreateRequest {

  @NotNull(message = ErrorMessageConstants.ORGANIZATION_LEAVE_LEAVE_TYPE_ID_NOT_NULL)
  private Long leaveTypeId;

  @NotNull(message = ErrorMessageConstants.ORGANIZATION_LEAVE_ORGANIZATION_ID_NOT_NULL)
  private Long organizationId;

  @NotNull(message = ErrorMessageConstants.ORGANIZATION_LEAVE_COUNT_NOT_NULL)
  private Long count;

  @AssertTrue(message = ErrorMessageConstants.ORGANIZATION_LEAVE_COUNT_CAN_NOT_BE_LESS_THAN_ONE)
  private boolean isValidCount() {
    return count > 0;
  }
}
