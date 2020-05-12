package com.niks.leaveservice.request.leave.allocation.organization;

import lombok.Data;

@Data
public class OrganizationLeaveAllocationUpdateRequest {

  private Long leaveTypeId;

  private Long organizationId;

  private Long count;
}
