package com.niks.leaveservice.service;

import com.niks.leaveservice.constants.ErrorMessageConstants;
import com.niks.leaveservice.model.builder.EmployeeLeaveAllocationBuilder;
import com.niks.leaveservice.model.db.Employee;
import com.niks.leaveservice.model.db.EmployeeLeaveAllocation;
import com.niks.leaveservice.model.db.LeaveType;
import com.niks.leaveservice.repository.EmployeeLeaveAllocationRepository;
import com.niks.leaveservice.request.leave.allocation.employee.EmployeeLeaveAllocationCreateRequest;
import com.niks.leaveservice.request.leave.allocation.employee.EmployeeLeaveAllocationUpdateRequest;
import com.niks.leaveservice.response.builder.leave.allocation.employee.EmployeeLeaveAllocationResponseBuilder;
import com.niks.leaveservice.response.leave.allocation.employee.EmployeeLeaveAllocationResponse;
import com.niks.leaveservice.service.client.EmployeeServiceClient;
import com.niks.leaveservice.service.exception.EmployeeServiceNotAvailableException;
import com.niks.leaveservice.service.exception.EntityAlreadyExistsException;
import com.niks.leaveservice.service.exception.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeLeaveAllocationService {

  @Autowired
  EmployeeLeaveAllocationRepository employeeLeaveAllocationRepository;

  @Autowired
  EmployeeLeaveAllocationBuilder employeeLeaveAllocationBuilder;

  @Autowired
  EmployeeLeaveAllocationResponseBuilder employeeLeaveAllocationResponseBuilder;

  @Autowired
  EmployeeServiceClient employeeServiceClient;

  @Autowired
  LeaveTypeService leaveTypeService;

  public EmployeeLeaveAllocationResponse getEmployeeAllocatedLeavesById(final Long id)
      throws EntityNotFoundException {
    EmployeeLeaveAllocation employeeLeaveAllocation = employeeLeaveAllocationRepository.findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(
                ErrorMessageConstants.EMPLOYEE_LEAVE_BY_ID_NOT_FOUND));
    return employeeLeaveAllocationResponseBuilder
        .buildFromResult(employeeLeaveAllocation);
  }

  public List<EmployeeLeaveAllocationResponse> getEmployeesAllocatedLeavesByEmployeeId(
      final Long employeeId) {
    return employeeLeaveAllocationResponseBuilder
        .buildFromResult(employeeLeaveAllocationRepository
            .findByEmployeeId(employeeId));
  }

  public EmployeeLeaveAllocation getEmployeeAllocatedLeavesByEmployeeIdAndLeaveType(
      final Long employeeId, Long leaveTypeId, final LocalDate startDate, final LocalDate endDate)
      throws EntityNotFoundException {
    List<EmployeeLeaveAllocation> leaveAllocationList = employeeLeaveAllocationRepository
        .findGivenTypeOfLeaveAllocatedOrNotInTheGivenDateRange(
            employeeId, leaveTypeId, startDate, endDate);
    if (!leaveAllocationList.isEmpty()) {
      return leaveAllocationList.get(0);
    } else {
      throw new EntityNotFoundException(
          ErrorMessageConstants.EMPLOYEE_LEAVE_GIVEN_TYPE_OF_LEAVE_NOT_ALLOCATED_FOR_GIVEN_EMPLOYEE);
    }
  }

  public EmployeeLeaveAllocationResponse assignLeavesToAnEmployee(
      final EmployeeLeaveAllocationCreateRequest employeeLeaveAllocationCreateRequest)
      throws EntityAlreadyExistsException, EntityNotFoundException {
    Optional<Employee> employee = employeeServiceClient
        .getEmployeeById(employeeLeaveAllocationCreateRequest.getEmployeeId());

    if (!employee.isPresent()) {
      throw new EmployeeServiceNotAvailableException(ErrorMessageConstants.EMPLOYEE_SERVICE_NOT_AVAILABLE);
    }
    LeaveType leaveType = leaveTypeService
        .getLeaveTypeById(employeeLeaveAllocationCreateRequest.getLeaveTypeId());

    isStartAndEndDateAreOverlappingWithExistingRecordForGivenEmployeeAndLeaveTypeId(
        employeeLeaveAllocationCreateRequest.getEmployeeId(), leaveType.getId(), employeeLeaveAllocationCreateRequest.getStartDate(),
        employeeLeaveAllocationCreateRequest.getEndDate());

    EmployeeLeaveAllocation employeeLeaveAllocationToCreate = employeeLeaveAllocationBuilder
        .buildFromRequest(employeeLeaveAllocationCreateRequest, leaveType);

    return employeeLeaveAllocationResponseBuilder
        .buildFromResult(employeeLeaveAllocationRepository.save(
            employeeLeaveAllocationToCreate));
  }

  public EmployeeLeaveAllocationResponse updateEmployeesAllocatedLeavesByEmployeeIdAndLeaveType(
      final Long id, final EmployeeLeaveAllocationUpdateRequest employeeLeaveAllocationUpdateRequest)
      throws EntityNotFoundException, EntityAlreadyExistsException {
    Optional<Employee> employee = employeeServiceClient
        .getEmployeeById(employeeLeaveAllocationUpdateRequest.getEmployeeId());

    if (!employee.isPresent()) {
      throw new EmployeeServiceNotAvailableException(ErrorMessageConstants.EMPLOYEE_SERVICE_NOT_AVAILABLE);
    }
    LeaveType leaveType = leaveTypeService
        .getLeaveTypeById(employeeLeaveAllocationUpdateRequest.getLeaveTypeId());

    if (employeeLeaveAllocationUpdateRequest.getStartDate() != null
        && employeeLeaveAllocationUpdateRequest.getEndDate() != null) {
      isStartAndEndDateAreOverlappingWithExistingRecordForGivenEmployeeAndLeaveTypeId(
          employeeLeaveAllocationUpdateRequest.getEmployeeId(), leaveType.getId(), employeeLeaveAllocationUpdateRequest.getStartDate(),
          employeeLeaveAllocationUpdateRequest.getEndDate());
    }

    Optional<EmployeeLeaveAllocation> employeeLeaveAllocationToUpdate =
        employeeLeaveAllocationRepository
            .findById(id);
    if (employeeLeaveAllocationToUpdate.isPresent()) {
      EmployeeLeaveAllocation updatedEmployeeLeaveAllocation =
          employeeLeaveAllocationBuilder.buildFromRequest(employeeLeaveAllocationUpdateRequest,
              employeeLeaveAllocationToUpdate.get(), leaveType);

      return employeeLeaveAllocationResponseBuilder
          .buildFromResult(employeeLeaveAllocationRepository.save(updatedEmployeeLeaveAllocation));
    } else {
      throw new EntityNotFoundException(
          ErrorMessageConstants.EMPLOYEE_LEAVE_ALLOCATED_BY_ID_NOT_FOUND);
    }
  }

  public void deleteAllocatedLeavesOfAnEmployeeByEmployeeIdAndLeaveType(final Long employeeId,
      final Long leaveTypeId)
      throws EntityNotFoundException {
    if (leaveTypeId != null) {
      if (employeeId != null) {
        employeeLeaveAllocationRepository
            .deleteByEmployeeIdAndLeaveTypeId(employeeId, leaveTypeService.getLeaveTypeById(leaveTypeId).getId());
      }
    } else if (employeeId != null) {
      employeeLeaveAllocationRepository
          .deleteByEmployeeId(employeeId);
    }
  }

  public List<EmployeeLeaveAllocationResponse> getAllocatedLeavesOfAllEmployees() {
    return employeeLeaveAllocationResponseBuilder
        .buildFromResult(employeeLeaveAllocationRepository.findAll());
  }

  private boolean isStartAndEndDateAreOverlappingWithExistingRecordForGivenEmployeeAndLeaveTypeId(
      final Long employeeId, final Long leaveTypeId, final LocalDate startDate, final LocalDate endDate)
      throws EntityAlreadyExistsException {
    List<EmployeeLeaveAllocation> employeeLeaveAllocationList = employeeLeaveAllocationRepository
        .findGivenTypeOfLeaveAllocatedOrNotInTheGivenDateRange(employeeId, leaveTypeId, startDate,
            endDate);
    if (!employeeLeaveAllocationList.isEmpty()) {
      throw new EntityAlreadyExistsException(
          ErrorMessageConstants.EMPLOYEE_LEAVE_ALLOCATED_WITH_GIVEN_LEAVE_TYPE_AND_START_DATE_AND_END_DATE_OVERLAPPING);
    } else {
      return false;
    }
  }
}
