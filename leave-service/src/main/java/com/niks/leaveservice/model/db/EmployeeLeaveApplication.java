package com.niks.leaveservice.model.db;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Table(name = "employee_leave_application")
public class EmployeeLeaveApplication extends  BaseModel{
  @Column(name = "start_date", nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @Column(name = "employee_id", nullable = false, length = 11)
  private Long employeeId;

  @OneToOne
  private LeaveType leaveType;

  @OneToOne
  private LeaveStatus leaveStatus;

  public EmployeeLeaveApplication() {

  }

  public EmployeeLeaveApplication(EmployeeLeaveApplication employeeLeaveApplication) {
    this.setId(employeeLeaveApplication.getId());
    this.setGuid(employeeLeaveApplication.getGuid());
    this.setStartDate(employeeLeaveApplication.getStartDate());
    this.setEndDate(employeeLeaveApplication.getEndDate());
    this.setCreatedAt(employeeLeaveApplication.getCreatedAt());
    this.setUpdatedAt(employeeLeaveApplication.getUpdatedAt());
    this.setEmployeeId(employeeLeaveApplication.getEmployeeId());
    this.setLeaveStatus(employeeLeaveApplication.getLeaveStatus());
    this.setLeaveType(employeeLeaveApplication.getLeaveType());
  }
}