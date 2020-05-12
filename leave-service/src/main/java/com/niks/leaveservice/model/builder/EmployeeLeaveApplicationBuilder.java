package com.niks.leaveservice.model.builder;

import com.niks.leaveservice.model.db.EmployeeLeaveApplication;
import com.niks.leaveservice.model.db.LeaveStatus;
import com.niks.leaveservice.model.db.LeaveType;
import com.niks.leaveservice.request.leave.application.employee.EmployeeLeaveApplicationCreateRequest;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class EmployeeLeaveApplicationBuilder {

  public EmployeeLeaveApplication buildFromRequest(
      final EmployeeLeaveApplicationCreateRequest employeeLeaveApplicationCreateRequest,
      final LeaveType leaveType, final LeaveStatus leaveStatus) {
    EmployeeLeaveApplication employeeLeaveApplication = new EmployeeLeaveApplication();
    employeeLeaveApplication.setGuid(UUID.randomUUID());
    employeeLeaveApplication.setLeaveType(leaveType);
    employeeLeaveApplication.setEmployeeId(employeeLeaveApplicationCreateRequest.getEmployeeId());
    employeeLeaveApplication.setLeaveStatus(leaveStatus);
    employeeLeaveApplication.setEndDate(employeeLeaveApplicationCreateRequest.getEndDate());
    employeeLeaveApplication.setStartDate(employeeLeaveApplicationCreateRequest.getStartDate());
    return employeeLeaveApplication;
  }

  public EmployeeLeaveApplication buildFromRequest(
      final EmployeeLeaveApplication employeeLeaveApplicationToBeUpdated,
      final LeaveStatus leaveStatus) {
    EmployeeLeaveApplication employeeLeaveApplicationAfterUpdate = new EmployeeLeaveApplication(
        employeeLeaveApplicationToBeUpdated);
    employeeLeaveApplicationAfterUpdate.setEmployeeId(employeeLeaveApplicationToBeUpdated.getEmployeeId());
    employeeLeaveApplicationAfterUpdate.setLeaveStatus(leaveStatus);
    return employeeLeaveApplicationAfterUpdate;
  }
}
