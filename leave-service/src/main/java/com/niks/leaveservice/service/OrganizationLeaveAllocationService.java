package com.niks.leaveservice.service;

import com.niks.leaveservice.constants.ErrorMessageConstants;
import com.niks.leaveservice.model.builder.OrganizationLeaveAllocationBuilder;
import com.niks.leaveservice.model.db.LeaveType;
import com.niks.leaveservice.model.db.Organization;
import com.niks.leaveservice.model.db.OrganizationLeaveAllocation;
import com.niks.leaveservice.repository.OrganizationLeaveAllocationRepository;
import com.niks.leaveservice.request.leave.allocation.organization.OrganizationLeaveAllocationCreateRequest;
import com.niks.leaveservice.request.leave.allocation.organization.OrganizationLeaveAllocationUpdateRequest;
import com.niks.leaveservice.service.client.OrganizationServiceClient;
import com.niks.leaveservice.service.exception.EntityAlreadyExistsException;
import com.niks.leaveservice.service.exception.EntityNotFoundException;
import com.niks.leaveservice.service.exception.OrganizationServiceNotAvailableException;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationLeaveAllocationService {

  @Autowired
  OrganizationLeaveAllocationRepository organizationLeaveAllocationRepository;

  @Autowired
  OrganizationServiceClient organizationServiceClient;

  @Autowired
  LeaveTypeService leaveTypeService;

  @Autowired
  OrganizationLeaveAllocationBuilder organizationLeaveAllocationBuilder;

  public List<OrganizationLeaveAllocation> getOrganizationAllocatedLeavesByOrganizationId(Long organizationId) {
    return organizationLeaveAllocationRepository.findByOrganizationId(organizationId);
  }

  public OrganizationLeaveAllocation allocateLeavesToAnOrganization(
      OrganizationLeaveAllocationCreateRequest organizationLeaveAllocationCreateRequest)
      throws EntityAlreadyExistsException, EntityNotFoundException {
    LeaveType leaveType = leaveTypeService.getLeaveTypeById(organizationLeaveAllocationCreateRequest.getLeaveTypeId());

    checkOrganizationIsExists(organizationLeaveAllocationCreateRequest.getOrganizationId());

    List<OrganizationLeaveAllocation> organizationLeaveList = organizationLeaveAllocationRepository
        .findByOrganizationIdAndLeaveTypeId(organizationLeaveAllocationCreateRequest.getOrganizationId(),
            organizationLeaveAllocationCreateRequest.getLeaveTypeId());
    if (organizationLeaveList.isEmpty()) {
      OrganizationLeaveAllocation organizationLeaveToCreate = organizationLeaveAllocationBuilder
          .buildFromRequest(organizationLeaveAllocationCreateRequest, leaveType);
      return organizationLeaveAllocationRepository
          .save(organizationLeaveToCreate);
    } else {
      throw new EntityAlreadyExistsException(
          ErrorMessageConstants.ORGANIZATION_LEAVE_WITH_ORGANIZATION_AND_LEAVE_ID_EXISTS);
    }
  }

  public OrganizationLeaveAllocation updateAllocatedLeavesOfAnOrganization(
      Long organizationLeaveId, OrganizationLeaveAllocationUpdateRequest organizationLeaveAllocationUpdateRequest
  ) throws EntityNotFoundException {
    LeaveType leaveType = null;

    if (organizationLeaveAllocationUpdateRequest.getOrganizationId() != null) {
      checkOrganizationIsExists(organizationLeaveAllocationUpdateRequest.getOrganizationId());
    }
    if (organizationLeaveAllocationUpdateRequest.getLeaveTypeId() != null) {
      leaveType = leaveTypeService.getLeaveTypeById(organizationLeaveAllocationUpdateRequest.getLeaveTypeId());
    }

    OrganizationLeaveAllocation organizationLeaveAllocationToUpdate = organizationLeaveAllocationRepository
        .findById(organizationLeaveId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_LEAVE_BY_ID_NOT_NULL));

    OrganizationLeaveAllocation organizationLeaveAfterUpdate = organizationLeaveAllocationBuilder
        .buildFromRequest(organizationLeaveAllocationToUpdate, organizationLeaveAllocationUpdateRequest, leaveType);

    return organizationLeaveAllocationRepository.save(organizationLeaveAfterUpdate);
  }

  public void deleteAllocatedLeaveOfAnOrganizationById(Long id) throws EntityNotFoundException {
    Optional<OrganizationLeaveAllocation> organizationLeaveToDelete = organizationLeaveAllocationRepository
        .findById(id);
    if (organizationLeaveToDelete.isPresent()) {
      organizationLeaveAllocationRepository.delete(organizationLeaveToDelete.get());
    } else {
      throw new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_LEAVE_BY_ID_NOT_NULL);
    }
  }

  public List<OrganizationLeaveAllocation> getAllOrganizationAllocatedLeaves() {
    return organizationLeaveAllocationRepository.findAll();
  }

  private void checkOrganizationIsExists(Long organizationId) {
    Optional<Organization> organization = organizationServiceClient
        .getOrganizationById(organizationId);

    if (!organization.isPresent()) {
      throw new OrganizationServiceNotAvailableException(ErrorMessageConstants.ORGANIZATION_SERVICE_NOT_AVAILABLE);
    }
  }
}
