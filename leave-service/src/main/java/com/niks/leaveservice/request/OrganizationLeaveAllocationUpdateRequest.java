package com.niks.leaveservice.request;

import lombok.Data;

@Data
public class OrganizationLeaveAllocationUpdateRequest {

  private Long leaveTypeId;

  private Long organizationId;

  private Long count;
}
