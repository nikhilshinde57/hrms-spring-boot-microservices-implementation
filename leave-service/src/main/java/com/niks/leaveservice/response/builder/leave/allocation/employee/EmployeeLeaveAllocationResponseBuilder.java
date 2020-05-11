package com.niks.leaveservice.response.builder.leave.allocation.employee;

import com.niks.leaveservice.model.db.EmployeeLeaveAllocation;
import com.niks.leaveservice.response.leave.allocation.employee.EmployeeLeaveAllocationResponse;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class EmployeeLeaveAllocationResponseBuilder {

  public EmployeeLeaveAllocationResponse buildFromResult(EmployeeLeaveAllocation employeeLeaveAllocation) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper
        .map(employeeLeaveAllocation, EmployeeLeaveAllocationResponse.class);
  }

  public List<EmployeeLeaveAllocationResponse> buildFromResult(List<EmployeeLeaveAllocation> employeeLeaveAllocationList) {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper
        .map(employeeLeaveAllocationList, new TypeToken<List<EmployeeLeaveAllocationResponse>>() {
        }.getType());
  }
}
