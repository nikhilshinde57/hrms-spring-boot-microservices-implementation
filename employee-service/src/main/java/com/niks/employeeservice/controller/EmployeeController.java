package com.niks.employeeservice.controller;

import com.niks.employeeservice.model.db.Employee;
import com.niks.employeeservice.request.employee.EmployeeCreateRequest;
import com.niks.employeeservice.request.employee.EmployeeSearchRequest;
import com.niks.employeeservice.request.employee.EmployeeUpdateRequest;
import com.niks.employeeservice.service.CustomUserInfoTokenServices;
import com.niks.employeeservice.service.EmployeeService;
import com.niks.employeeservice.service.exception.BadRequestException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @Autowired
  ResourceServerProperties resourceServerProperties;

  @Bean
  @Primary
  public ResourceServerTokenServices myUserInfoTokenServices() {
    return new CustomUserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
  }


  @GetMapping(value = "/{id}")
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public Employee getEmployeeById(
      @PathVariable Long id) {
    return employeeService.getEmployeeById(id);
  }

  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public Employee createEmployee(
      @Valid @RequestBody @NotNull EmployeeCreateRequest employeeCreateRequest)
      throws BadRequestException {
    return employeeService.createEmployee(employeeCreateRequest);
  }

  @PostMapping(value = "/search")
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public List<Employee> searchEmployee(
      @Valid @RequestBody @NotNull EmployeeSearchRequest employeeSearchRequest) {
    return employeeService.searchEmployee(employeeSearchRequest);
  }

  @PatchMapping(value = "/{id}")
  @RolesAllowed({ "ROLE_USER", "ROLE_ADMIN"})
  public Employee updateEmployeeById(
      @PathVariable Long id,
      @NotNull @Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest)
      throws BadRequestException {
    return employeeService.updateEmployee(id, employeeUpdateRequest);
  }

  @DeleteMapping(value = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void deleteEmployeeById(@PathVariable("id") Long id) {
    employeeService.deleteEmployeeById(id);
  }

  @GetMapping(value = "")
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public List<Employee> getAllEmployees() {
    return employeeService.getAllEmployees();
  }
}