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
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Table(name = "employee_leave_allocation")
public class EmployeeLeaveAllocation  extends BaseModel {

  @Column(name = "employee_id", nullable = false, length = 11)
  private Long employeeId;

  @OneToOne
  LeaveType leaveType;

  @Column(name = "start_date", nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private LocalDate endDate;

  @Column(name = "allotted_leaves", nullable = false)
  private Long allottedLeaves;

  @Column(name = "carried_leaves", nullable = false)
  private Long carriedLeaves;

  @Column(name = "additional_leaves", nullable = false)
  private Long additionalLeaves;

  @Column(name = "total_leaves", nullable = false)
  private Long totalLeaves;

  public EmployeeLeaveAllocation() {
  }

  public EmployeeLeaveAllocation(EmployeeLeaveAllocation employeeLeaveAllocation) {
    this.setId(employeeLeaveAllocation.getId());
    this.setGuid(employeeLeaveAllocation.getGuid());
    this.setLeaveType(employeeLeaveAllocation.getLeaveType());
    this.setEmployeeId(employeeLeaveAllocation.getEmployeeId());
    this.setAdditionalLeaves(employeeLeaveAllocation.getAdditionalLeaves());
    this.setAllottedLeaves(employeeLeaveAllocation.getAllottedLeaves());
    this.setCarriedLeaves(employeeLeaveAllocation.getCarriedLeaves());
    this.setTotalLeaves(employeeLeaveAllocation.getTotalLeaves());
    this.setStartDate(employeeLeaveAllocation.getStartDate());
    this.setEndDate(employeeLeaveAllocation.getEndDate());
    this.setCreatedAt(employeeLeaveAllocation.getCreatedAt());
    this.setUpdatedAt(employeeLeaveAllocation.getUpdatedAt());
  }
}
