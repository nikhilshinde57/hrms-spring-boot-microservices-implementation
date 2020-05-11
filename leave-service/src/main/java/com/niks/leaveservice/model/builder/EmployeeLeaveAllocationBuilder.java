package com.niks.leaveservice.model.builder;

import com.niks.leaveservice.model.db.EmployeeLeaveAllocation;
import com.niks.leaveservice.model.db.LeaveType;
import com.niks.leaveservice.request.leave.allocation.employee.EmployeeLeaveAllocationCreateRequest;
import com.niks.leaveservice.request.leave.allocation.employee.EmployeeLeaveAllocationUpdateRequest;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class EmployeeLeaveAllocationBuilder {

  public EmployeeLeaveAllocation buildFromRequest(
      final EmployeeLeaveAllocationCreateRequest employeeLeaveAllocationCreateRequest,
       final LeaveType leaveType) {
    EmployeeLeaveAllocation  employeeLeaveAllocation = new EmployeeLeaveAllocation();
    employeeLeaveAllocation.setGuid(UUID.randomUUID());
    employeeLeaveAllocation.setEmployeeId(employeeLeaveAllocationCreateRequest.getEmployeeId());
    employeeLeaveAllocation.setLeaveType(leaveType);
    employeeLeaveAllocation.setStartDate(employeeLeaveAllocationCreateRequest.getStartDate());
    employeeLeaveAllocation.setEndDate(employeeLeaveAllocationCreateRequest.getEndDate());
    employeeLeaveAllocation
        .setAllottedLeaves(employeeLeaveAllocationCreateRequest.getAllottedLeaves());
    employeeLeaveAllocation
        .setAdditionalLeaves(employeeLeaveAllocationCreateRequest.getAdditionalLeaves());
    employeeLeaveAllocation
        .setCarriedLeaves(employeeLeaveAllocationCreateRequest.getCarriedLeaves());
    employeeLeaveAllocation.setTotalLeaves(getTotalLeaves(employeeLeaveAllocation));
    return employeeLeaveAllocation;
  }

  public EmployeeLeaveAllocation buildFromRequest(
      final EmployeeLeaveAllocationUpdateRequest employeeLeaveAllocationUpdateRequest,
      final EmployeeLeaveAllocation employeeLeaveAllocationToBeUpdated,
      final LeaveType leaveType) {
    EmployeeLeaveAllocation employeeLeaveAllocationAfterUpdate = new EmployeeLeaveAllocation(
        employeeLeaveAllocationToBeUpdated);

    if (employeeLeaveAllocationUpdateRequest.getEmployeeId() != null) {
      employeeLeaveAllocationAfterUpdate.setEmployeeId(employeeLeaveAllocationUpdateRequest.getEmployeeId());
    }
    if (employeeLeaveAllocationUpdateRequest.getLeaveTypeId() != null) {
      employeeLeaveAllocationAfterUpdate.setLeaveType(leaveType);
    }

    if (employeeLeaveAllocationUpdateRequest.getAllottedLeaves() != null) {
      employeeLeaveAllocationAfterUpdate
          .setAllottedLeaves(employeeLeaveAllocationUpdateRequest.getAllottedLeaves());
    }

    if (employeeLeaveAllocationUpdateRequest.getAdditionalLeaves() != null) {
      employeeLeaveAllocationAfterUpdate
          .setAdditionalLeaves(employeeLeaveAllocationUpdateRequest.getAdditionalLeaves());
    }

    if (employeeLeaveAllocationUpdateRequest.getCarriedLeaves() != null) {
      employeeLeaveAllocationAfterUpdate
          .setCarriedLeaves(employeeLeaveAllocationUpdateRequest.getCarriedLeaves());
    }

    if (employeeLeaveAllocationUpdateRequest.getStartDate() != null) {
      employeeLeaveAllocationAfterUpdate
          .setStartDate(employeeLeaveAllocationUpdateRequest.getStartDate());
    }

    if (employeeLeaveAllocationUpdateRequest.getEndDate() != null) {
      employeeLeaveAllocationAfterUpdate
          .setEndDate(employeeLeaveAllocationUpdateRequest.getEndDate());
    }

    employeeLeaveAllocationAfterUpdate
        .setTotalLeaves(getTotalLeaves(employeeLeaveAllocationAfterUpdate));

    return employeeLeaveAllocationAfterUpdate;
  }

  private Long getTotalLeaves(final EmployeeLeaveAllocation employeeLeaveAllocation) {
    return employeeLeaveAllocation.getAdditionalLeaves() + employeeLeaveAllocation
        .getCarriedLeaves() + employeeLeaveAllocation.getAllottedLeaves();
  }
}
