package com.niks.leaveservice.model.builder;

import com.niks.leaveservice.model.db.LeaveType;
import com.niks.leaveservice.model.db.OrganizationLeaveAllocation;
import com.niks.leaveservice.request.leave.allocation.organization.OrganizationLeaveAllocationCreateRequest;
import com.niks.leaveservice.request.leave.allocation.organization.OrganizationLeaveAllocationUpdateRequest;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class OrganizationLeaveAllocationBuilder {

  public OrganizationLeaveAllocation buildFromRequest(
      OrganizationLeaveAllocationCreateRequest organizationLeaveAllocationCreateRequest, LeaveType leaveType) {
    OrganizationLeaveAllocation organizationLeaveAllocation = new OrganizationLeaveAllocation();
    organizationLeaveAllocation.setGuid(UUID.randomUUID());
    organizationLeaveAllocation.setLeaveType(leaveType);
    organizationLeaveAllocation.setOrganizationId(organizationLeaveAllocationCreateRequest.getOrganizationId());
    organizationLeaveAllocation.setCount(organizationLeaveAllocationCreateRequest.getCount());
    return organizationLeaveAllocation;
  }

  public OrganizationLeaveAllocation buildFromRequest(OrganizationLeaveAllocation organizationLeaveAllocationToBeUpdated,
      OrganizationLeaveAllocationUpdateRequest organizationLeaveAllocationUpdateRequest, LeaveType leaveType) {
    OrganizationLeaveAllocation organizationLeaveAllocationAfterUpdate = new OrganizationLeaveAllocation(
        organizationLeaveAllocationToBeUpdated);

    if (organizationLeaveAllocationUpdateRequest.getCount() != null) {
      organizationLeaveAllocationAfterUpdate.setCount(organizationLeaveAllocationUpdateRequest.getCount());
    }
    if (organizationLeaveAllocationUpdateRequest.getOrganizationId() != null) {
      organizationLeaveAllocationAfterUpdate
          .setOrganizationId(organizationLeaveAllocationUpdateRequest.getOrganizationId());
    }
    if (leaveType != null) {
      organizationLeaveAllocationAfterUpdate.setLeaveType(leaveType);
    }
    return organizationLeaveAllocationAfterUpdate;
  }
}
