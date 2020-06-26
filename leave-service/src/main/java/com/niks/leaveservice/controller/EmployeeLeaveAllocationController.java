package com.niks.leaveservice.controller;

import com.niks.leaveservice.request.leave.allocation.employee.EmployeeLeaveAllocationCreateRequest;
import com.niks.leaveservice.request.leave.allocation.employee.EmployeeLeaveAllocationUpdateRequest;
import com.niks.leaveservice.response.leave.allocation.employee.EmployeeLeaveAllocationResponse;
import com.niks.leaveservice.service.CustomUserInfoTokenServices;
import com.niks.leaveservice.service.EmployeeLeaveAllocationService;
import com.niks.leaveservice.service.exception.ServiceException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leave-allocation/employees")
public class EmployeeLeaveAllocationController {
  @Autowired
  EmployeeLeaveAllocationService employeeLeaveAllocationService;

  @Autowired
  ResourceServerProperties resourceServerProperties;

  @Bean
  @Primary
  public ResourceServerTokenServices myUserInfoTokenServices() {
    return new CustomUserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
  }

  @GetMapping(value = "")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public List<EmployeeLeaveAllocationResponse> getAllocatedLeavesOfAllEmployees() {
    return employeeLeaveAllocationService.getAllocatedLeavesOfAllEmployees();
  }

  @GetMapping(value = "", params = "employeeId")
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public List<EmployeeLeaveAllocationResponse> getAllocatedLeavesOfAnEmployee(
      @RequestParam("employeeId") final Long employeeId) {
    return employeeLeaveAllocationService.getEmployeesAllocatedLeavesByEmployeeId(employeeId);
  }

  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public EmployeeLeaveAllocationResponse allocateLeavesToAnEmployee(
      @Valid @RequestBody @NotNull final EmployeeLeaveAllocationCreateRequest employeeLeaveAllocationCreateRequest)
      throws ServiceException {
    return employeeLeaveAllocationService.assignLeavesToAnEmployee(employeeLeaveAllocationCreateRequest);
  }

  @PatchMapping(value = "/{id}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public EmployeeLeaveAllocationResponse updateAllocatedLeavesOfAnEmployeeByEmployeeIdAndLeaveTypeId(
      @PathVariable Long id,
      @NotNull @Valid @RequestBody final
      EmployeeLeaveAllocationUpdateRequest employeeLeaveAllocationUpdateRequest)
      throws ServiceException {
    return employeeLeaveAllocationService
        .updateEmployeesAllocatedLeavesByEmployeeIdAndLeaveType(id, employeeLeaveAllocationUpdateRequest);
  }

  @DeleteMapping(value = "", params = {"employeeId", "leaveTypeId"})
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void deleteAllocatedLeavesOfAnEmployeeByEmployeeIdAndLeaveTypeId(
      @RequestParam(required = true) final Long employeeId,
      @RequestParam(required = false) final Long leaveTypeId)
      throws ServiceException {
    employeeLeaveAllocationService
        .deleteAllocatedLeavesOfAnEmployeeByEmployeeIdAndLeaveType(employeeId, leaveTypeId);
  }

}