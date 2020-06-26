package com.niks.leaveservice.controller;

import com.niks.leaveservice.request.leave.application.employee.EmployeeLeaveApplicationCreateRequest;
import com.niks.leaveservice.request.leave.application.employee.EmployeeLeaveApplicationUpdateRequest;
import com.niks.leaveservice.response.leave.allocation.application.employee.EmployeeLeaveApplicationResponse;
import com.niks.leaveservice.service.CustomUserInfoTokenServices;
import com.niks.leaveservice.service.EmployeeLeaveApplicationService;
import com.niks.leaveservice.service.exception.EntityNotFoundException;
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
@RequestMapping("/api/leave-application/employees")
public class EmployeeLeaveApplicationController {

  @Autowired
  private EmployeeLeaveApplicationService employeeLeaveApplicationService;

  @Autowired
  ResourceServerProperties resourceServerProperties;

  @Bean
  @Primary
  public ResourceServerTokenServices myUserInfoTokenServices() {
    return new CustomUserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
  }

  @GetMapping(value = "", params = {"employeeId", "leaveTypeId", "leaveStatusId"})
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public List<EmployeeLeaveApplicationResponse> getAllAppliedLeavesOfAnEmployeeByEmployeeIdLeaveTypeAndLeaveStatus(
      @RequestParam final Long employeeId,
      @RequestParam(required = false) final Long leaveTypeId,
      @RequestParam(required = false) final Long leaveStatusId)
      throws ServiceException {
    return employeeLeaveApplicationService.getLeaveApplicationByEmployeeIdLeaveTypeAndLeaveStatus(
        employeeId, leaveTypeId, leaveStatusId);
  }

  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public EmployeeLeaveApplicationResponse applyLeaveOfAnEmployee(
      @Valid @RequestBody @NotNull final EmployeeLeaveApplicationCreateRequest employeeLeaveApplicationCreateRequest)
      throws ServiceException {
    return employeeLeaveApplicationService
        .applyLeave(employeeLeaveApplicationCreateRequest);
  }

  @PatchMapping(value = "/{id}")
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public EmployeeLeaveApplicationResponse updateAppliedLeaveOfAnEmployee(
      @PathVariable Long id,
      @Valid @RequestBody @NotNull final EmployeeLeaveApplicationUpdateRequest employeeLeaveApplicationUpdateRequest)
      throws EntityNotFoundException {
    return employeeLeaveApplicationService.updateLeaveApplication(id, employeeLeaveApplicationUpdateRequest);
  }

  @GetMapping(value = "")
  @RolesAllowed({ "ROLE_TL", "ROLE_ADMIN"})
  public List<EmployeeLeaveApplicationResponse> getAllAppliedLeavesOfAllEmployees() {
    return employeeLeaveApplicationService.getAllAppliedLeavesOfAllEmployees();
  }
}