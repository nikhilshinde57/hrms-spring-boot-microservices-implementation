package com.niks.leaveservice.model.db;

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
@Table(name = "organization_leave_allocation")
public class OrganizationLeaveAllocation extends BaseModel{

  private static final long serialVersionUID = 1L;
  @Column(name = "count", nullable = false, unique = true, length = 11)
  private Long count;
  @Column(name = "organization_id", nullable = false, unique = true, length = 11)
  private Long organizationId;
  @OneToOne
  private LeaveType leaveType;


  public OrganizationLeaveAllocation() {

  }

  public OrganizationLeaveAllocation(OrganizationLeaveAllocation organizationLeaveAllocation) {
    this.setId(organizationLeaveAllocation.getId());
    this.setGuid(organizationLeaveAllocation.getGuid());
    this.setLeaveType(organizationLeaveAllocation.getLeaveType());
    this.setOrganizationId(organizationLeaveAllocation.getOrganizationId());
    this.setCount(organizationLeaveAllocation.getCount());
    this.setCreatedAt(organizationLeaveAllocation.getCreatedAt());
  }
}
