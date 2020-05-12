package com.niks.employeeservice.model.builder;

import com.niks.employeeservice.model.db.Employee;
import com.niks.employeeservice.repository.EmployeeRepository;
import com.niks.employeeservice.request.employee.EmployeeCreateRequest;
import com.niks.employeeservice.request.employee.EmployeeUpdateRequest;
import com.niks.employeeservice.service.exception.BadRequestException;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeBuilder {

  @Autowired
  private EmployeeRepository employeeRepository;

  public Employee buildFromRequest(EmployeeCreateRequest employeeCreateRequest, Employee reportsTo)
      throws BadRequestException {
    Employee employee = new Employee();
    employee.setEmpId(employeeCreateRequest.getEmpId());
    employee.setOrganizationId(employeeCreateRequest.getOrganizationId());
    employee.setFirstName(employeeCreateRequest.getFirstName());
    employee.setMiddleName(employeeCreateRequest.getMiddleName());
    employee.setLastName(employeeCreateRequest.getLastName());
    employee.setDateOfBirth(employeeCreateRequest.getDateOfBirth());
    employee.setIsActive(
        !(employeeCreateRequest.getIsActive() != null && !employeeCreateRequest.getIsActive()));
    employee.setEmail(employeeCreateRequest.getEmail());
    employee.setJoiningDate(employeeCreateRequest.getJoiningDate());
    employee.setExitDate(employeeCreateRequest.getExitDate());
    employee.setAddress(employeeCreateRequest.getAddress());
    employee.setContact(employeeCreateRequest.getContact());
    employee.setDesignation(employeeCreateRequest.getDesignation());
    employee.setGuid(UUID.randomUUID());
    //if reports to is available then do validation of data
    checkAndUpdateReportsTo(employee, reportsTo, employeeCreateRequest.getOrganizationId());

    return employee;
  }

  public Employee buildFromRequest(Employee employeeToBeUpdated,
      EmployeeUpdateRequest employeeUpdateRequest, final Employee reportsTo) throws BadRequestException {
    Employee employeeAfterUpdate = new Employee(employeeToBeUpdated);
    if (employeeUpdateRequest.getEmpId() != null) {
      employeeAfterUpdate.setEmpId(employeeUpdateRequest.getEmpId());
    }
    if (employeeUpdateRequest.getOrganizationId() != null) {
      employeeAfterUpdate.setOrganizationId(employeeUpdateRequest.getOrganizationId());
    }
    if (StringUtils.isNotBlank(employeeUpdateRequest.getFirstName())) {
      employeeAfterUpdate.setFirstName(employeeUpdateRequest.getFirstName());
    }
    if (StringUtils.isNotBlank(employeeUpdateRequest.getMiddleName())) {
      employeeAfterUpdate.setMiddleName(employeeUpdateRequest.getMiddleName());
    }

    if (StringUtils.isNotBlank(employeeUpdateRequest.getLastName())) {
      employeeAfterUpdate.setLastName(employeeUpdateRequest.getLastName());
    }

    if (employeeUpdateRequest.getDateOfBirth() != null) {
      employeeAfterUpdate.setDateOfBirth(employeeUpdateRequest.getDateOfBirth());
    }

    if (employeeUpdateRequest.getIsActive() != null) {
      employeeAfterUpdate.setIsActive(employeeUpdateRequest.getIsActive());
    }

    if (StringUtils.isNotBlank(employeeUpdateRequest.getEmail())) {
      employeeAfterUpdate.setEmail(employeeUpdateRequest.getEmail());
    }

    if (employeeUpdateRequest.getJoiningDate() != null) {
      employeeAfterUpdate.setJoiningDate(employeeUpdateRequest.getJoiningDate());
    }

    if (employeeUpdateRequest.getExitDate() != null) {
      employeeAfterUpdate.setExitDate(employeeUpdateRequest.getExitDate());
    }

    if (StringUtils.isNotBlank(employeeUpdateRequest.getAddress())) {
      employeeAfterUpdate.setAddress(employeeUpdateRequest.getAddress());
    }

    if (StringUtils.isNotBlank(employeeUpdateRequest.getContact())) {
      employeeAfterUpdate.setContact(employeeUpdateRequest.getContact());
    }

    if (StringUtils.isNotBlank(employeeUpdateRequest.getDesignation())) {
      employeeAfterUpdate.setDesignation(employeeUpdateRequest.getDesignation());
    }
    checkAndUpdateReportsTo(employeeAfterUpdate, reportsTo,
        employeeAfterUpdate.getOrganizationId());

    return employeeAfterUpdate;
  }

  private void checkAndUpdateReportsTo(final Employee employee, final Employee reportsTo,
      final Long organizationId)
      throws BadRequestException {
    //if reports to is available then do validation of data
    if (reportsTo != null) {
      if (employeeRepository.existsByIdAndOrganizationId(reportsTo.getId(), organizationId)) {
        employee.setReportsTo(reportsTo);
      } else {
        throw new BadRequestException("Invalid value for reports to field");
      }
    }
  }
}
