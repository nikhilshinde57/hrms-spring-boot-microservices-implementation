package com.niks.leaveservice.response.builder.leave.application.employee;

import com.niks.leaveservice.model.db.EmployeeLeaveApplication;
import com.niks.leaveservice.response.leave.allocation.application.employee.EmployeeLeaveApplicationResponse;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class EmployeeLeaveApplicationResponseBuilder {

  public EmployeeLeaveApplicationResponse buildFromResult(
      EmployeeLeaveApplication employeeLeaveApplication) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper
        .map(employeeLeaveApplication, EmployeeLeaveApplicationResponse.class);
  }

  public List<EmployeeLeaveApplicationResponse> buildFromResult(
      List<EmployeeLeaveApplication> employeeLeaveApplicationList) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper
        .map(employeeLeaveApplicationList, new TypeToken<List<EmployeeLeaveApplicationResponse>>() {
        }.getType());
  }
}