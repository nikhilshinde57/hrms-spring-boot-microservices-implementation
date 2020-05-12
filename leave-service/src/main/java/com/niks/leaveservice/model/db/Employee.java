package com.niks.leaveservice.model.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;


@Data
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class Employee extends BaseModel {

  private static final long serialVersionUID = 1L;
  private String empId;
  @EqualsAndHashCode.Exclude
  private Long organizationId;
  private String firstName;
  private String middleName;
  private String lastName;
  @EqualsAndHashCode.Exclude
  private Date dateOfBirth;
  @EqualsAndHashCode.Exclude
  private Boolean isActive;
  private String email;
  @EqualsAndHashCode.Exclude
  private Date joiningDate;
  @EqualsAndHashCode.Exclude
  private Date exitDate;
  @EqualsAndHashCode.Exclude
  private String address;
  @EqualsAndHashCode.Exclude
  private String contact;
  @EqualsAndHashCode.Exclude
  private String designation;
  @EqualsAndHashCode.Exclude
  private Employee reportsTo;

  public Employee() {

  }

  public Employee(Employee employee) {
    this.setId(employee.getId());
    this.setGuid(employee.getGuid());
    this.setEmpId(employee.getEmpId());
    this.setOrganizationId(employee.getOrganizationId());
    this.setFirstName(employee.getFirstName());
    this.setMiddleName(employee.getMiddleName());
    this.setLastName(employee.getLastName());
    this.setDateOfBirth(employee.getDateOfBirth());
    this.setIsActive(employee.getIsActive());
    this.setEmail(employee.getEmail());
    this.setJoiningDate(employee.getJoiningDate());
    this.setExitDate(employee.getExitDate());
    this.setAddress(employee.getAddress());
    this.setContact(employee.getContact());
    this.setDesignation(employee.getDesignation());
    this.setCreatedAt(employee.getCreatedAt());
    this.setReportsTo(employee.getReportsTo());
  }

}
