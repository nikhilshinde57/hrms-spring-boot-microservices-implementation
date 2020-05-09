package com.niks.leaveservice.controller;

import com.niks.leaveservice.model.db.OrganizationLeaveAllocation;
import com.niks.leaveservice.request.OrganizationLeaveAllocationCreateRequest;
import com.niks.leaveservice.request.OrganizationLeaveAllocationUpdateRequest;
import com.niks.leaveservice.service.OrganizationLeaveAllocationService;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/api/leave-allocation/organizations")
public class OrganizationLeaveAllocationController {

  @Autowired
  OrganizationLeaveAllocationService organizationLeaveAllocationService;

  /**
   * GET /api/organization-leaves?organizationId={organizationId}
   */
  @GetMapping(value = "", params = "organizationId")
  public List<OrganizationLeaveAllocation> getOrganizationLeavesByOrganizationId(
      @RequestParam(name = "organizationId", required = true) Long organizationId) {
    return organizationLeaveAllocationService.getOrganizationAllocatedLeavesByOrganizationId(organizationId);
  }

  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  public OrganizationLeaveAllocation addOrganizationLeave(
      @Valid @RequestBody @NotNull OrganizationLeaveAllocationCreateRequest organizationLeaveAllocationCreateRequest)
      throws ServiceException {
    return organizationLeaveAllocationService.allocateLeavesToAnOrganization(organizationLeaveAllocationCreateRequest);
  }

  @PatchMapping(value = "/{organizationLeaveId}")
  public OrganizationLeaveAllocation updateOrganizationLeave(@PathVariable Long organizationLeaveId,
      @NotNull @Valid @RequestBody OrganizationLeaveAllocationUpdateRequest organizationLeaveAllocationUpdateRequest)
      throws ServiceException {
    return organizationLeaveAllocationService.updateAllocatedLeavesOfAnOrganization(organizationLeaveId, organizationLeaveAllocationUpdateRequest);
  }

  @DeleteMapping(value = "/{organizationLeaveId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteOrganizationLeaveById(
      @PathVariable Long organizationLeaveId)
      throws ServiceException {
    organizationLeaveAllocationService.deleteAllocatedLeaveOfAnOrganizationById(organizationLeaveId);
  }

  @GetMapping(value = "")
  public List<OrganizationLeaveAllocation> getAllOrganizationLeaves() {
    return organizationLeaveAllocationService.getAllOrganizationAllocatedLeaves();
  }
}