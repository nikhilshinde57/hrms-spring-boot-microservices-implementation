package com.niks.leaveservice.response.leave.allocation.application.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class EmployeeLeaveApplicationResponse {

  private Long id;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime createdAt;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime updatedAt;

  private Employee employee;

  private LeaveType leaveType;

  private LeaveStatus leaveStatus;

}

@Data
class Employee {

  private Long id;

  private Long employeeId;

  //private Long organizationId;
}

@Data
class LeaveType {

  private Long id;

  private String type;
}

@Data
class LeaveStatus {

  private Long id;

  private String status;
}