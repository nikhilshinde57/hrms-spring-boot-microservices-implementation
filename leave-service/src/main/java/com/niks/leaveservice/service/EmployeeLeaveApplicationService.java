package com.niks.leaveservice.service;

import com.niks.leaveservice.constants.ErrorMessageConstants;
import com.niks.leaveservice.model.builder.EmployeeLeaveApplicationBuilder;
import com.niks.leaveservice.model.db.Employee;
import com.niks.leaveservice.model.db.EmployeeLeaveAllocation;
import com.niks.leaveservice.model.db.EmployeeLeaveApplication;
import com.niks.leaveservice.model.db.LeaveStatus;
import com.niks.leaveservice.model.db.LeaveType;
import com.niks.leaveservice.repository.EmployeeLeaveApplicationRepository;
import com.niks.leaveservice.request.leave.application.employee.EmployeeLeaveApplicationCreateRequest;
import com.niks.leaveservice.request.leave.application.employee.EmployeeLeaveApplicationUpdateRequest;
import com.niks.leaveservice.response.builder.leave.application.employee.EmployeeLeaveApplicationResponseBuilder;
import com.niks.leaveservice.response.leave.allocation.application.employee.EmployeeLeaveApplicationResponse;
import com.niks.leaveservice.service.client.EmployeeServiceClient;
import com.niks.leaveservice.service.exception.EmployeeServiceNotAvailableException;
import com.niks.leaveservice.service.exception.EntityAlreadyExistsException;
import com.niks.leaveservice.service.exception.EntityNotFoundException;
import com.niks.leaveservice.service.exception.InsufficientLeaveException;

import static com.niks.leaveservice.enums.LeaveStatus.CANCELED;
import static com.niks.leaveservice.enums.LeaveStatus.REJECTED;
import static com.niks.leaveservice.enums.LeaveStatus.PENDING;
import static com.niks.leaveservice.enums.LeaveStatus.REJECTED;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeLeaveApplicationService {

  @Autowired
  EmployeeLeaveApplicationRepository employeeLeaveApplicationRepository;

  @Autowired
  EmployeeServiceClient employeeServiceClient;

  @Autowired
  LeaveTypeService leaveTypeService;

  @Autowired
  LeaveStatusService leaveStatusService;

  @Autowired
  EmployeeLeaveAllocationService employeeLeaveAllocationService;

  @Autowired
  EmployeeLeaveApplicationBuilder employeeLeaveApplicationBuilder;

  @Autowired
  EmployeeLeaveApplicationResponseBuilder employeeLeaveApplicationResponseBuilder;

  public List<EmployeeLeaveApplicationResponse> getAllAppliedLeavesOfAllEmployees() {
    return employeeLeaveApplicationResponseBuilder.buildFromResult(employeeLeaveApplicationRepository.findAll());
  }

  public EmployeeLeaveApplicationResponse getLeaveById(final Long id) throws EntityNotFoundException {
    EmployeeLeaveApplication employeeLeaveApplication
        = employeeLeaveApplicationRepository.findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorMessageConstants.LEAVE_BY_ID_NOT_FOUND));
    return employeeLeaveApplicationResponseBuilder.buildFromResult(employeeLeaveApplication);
  }

  public List<EmployeeLeaveApplicationResponse> getLeaveApplicationByEmployeeIdLeaveTypeAndLeaveStatus(
      final Long employeeId, final Long leaveTypeId, final Long leaveStatusId)
      throws EntityNotFoundException {

    Optional<Employee> employee = employeeServiceClient
        .getEmployeeById(employeeId);

    if (!employee.isPresent()) {
      throw new EmployeeServiceNotAvailableException(ErrorMessageConstants.EMPLOYEE_SERVICE_NOT_AVAILABLE);
    }

    if (null == leaveStatusId) {
      if (employeeId != null && leaveTypeId == null) {
        return employeeLeaveApplicationResponseBuilder
            .buildFromResult(employeeLeaveApplicationRepository.findByEmployeeId(employeeId));
      } else if (employeeId != null) {
        return employeeLeaveApplicationResponseBuilder.buildFromResult(employeeLeaveApplicationRepository
            .findByEmployeeIdAndLeaveTypeId(employeeId, leaveTypeId));
      }
    } else if (employeeId != null && leaveTypeId != null) {
      return employeeLeaveApplicationResponseBuilder.buildFromResult(employeeLeaveApplicationRepository
          .findByEmployeeIdAndLeaveTypeIdAndLeaveStatusId(employeeId, leaveTypeId,
              leaveStatusId));
    }
    return employeeLeaveApplicationResponseBuilder.buildFromResult(new ArrayList<EmployeeLeaveApplication>());
  }

  public EmployeeLeaveApplicationResponse applyLeave(
      final EmployeeLeaveApplicationCreateRequest employeeLeaveApplicationCreateRequest)
      throws EntityNotFoundException, InsufficientLeaveException,
      EntityAlreadyExistsException {

    Optional<Employee> employee = employeeServiceClient
        .getEmployeeById(employeeLeaveApplicationCreateRequest.getEmployeeId());

    if (!employee.isPresent()) {
      throw new EmployeeServiceNotAvailableException(ErrorMessageConstants.EMPLOYEE_SERVICE_NOT_AVAILABLE);
    }

    LeaveType leaveType = leaveTypeService
        .getLeaveTypeById(employeeLeaveApplicationCreateRequest.getLeaveTypeId());

    int noOfDaysLeaveApplied = getNoOfDaysLeaveApplied(
        employeeLeaveApplicationCreateRequest.getStartDate(),
        employeeLeaveApplicationCreateRequest.getEndDate());

    EmployeeLeaveAllocation allocatedEmployeeLeaves = employeeLeaveAllocationService
        .getEmployeeAllocatedLeavesByEmployeeIdAndLeaveType(employeeLeaveApplicationCreateRequest.getEmployeeId(),
            leaveType.getId(), employeeLeaveApplicationCreateRequest.getStartDate(),
            employeeLeaveApplicationCreateRequest.getEndDate());

    Collection<Long> leaveStatusIds = new ArrayList<>();
    leaveStatusIds.add(leaveStatusService.getLeaveStatusByStatus(REJECTED
        .toString()).getId());
    leaveStatusIds.add(leaveStatusService.getLeaveStatusByStatus(CANCELED.toString()).getId());

    List<EmployeeLeaveApplication> appliedLeaves = employeeLeaveApplicationRepository
        .findByEmployeeIdAndLeaveTypeIdAndLeaveStatusIdNotIn(employeeLeaveApplicationCreateRequest.getEmployeeId(), leaveType
            .getId(), leaveStatusIds);

    int availableLeaves = (int) (allocatedEmployeeLeaves.getTotalLeaves() - appliedLeaves.size());

    if (availableLeaves < noOfDaysLeaveApplied) {
      throw new InsufficientLeaveException(
          ErrorMessageConstants.LEAVE_INSUFFICIENT_LEAVE_COUNT_OF_GIVEN_LEAVE_TYPE);
    } else {
      isStartAndEndDateAreOverlappingWithExistingRecordForGivenEmployeeId(
          employeeLeaveApplicationCreateRequest.getEmployeeId(),
          employeeLeaveApplicationCreateRequest.getStartDate(),
          employeeLeaveApplicationCreateRequest.getEndDate());
      EmployeeLeaveApplication leaveToApply = employeeLeaveApplicationRepository
          .findByEmployeeIdAndStartDateAndEndDate(
              employeeLeaveApplicationCreateRequest.getEmployeeId(),
              employeeLeaveApplicationCreateRequest.getStartDate(),
              employeeLeaveApplicationCreateRequest.getEndDate());
      if (leaveToApply == null) {
        leaveToApply = employeeLeaveApplicationBuilder
            .buildFromRequest(employeeLeaveApplicationCreateRequest, leaveType,
                leaveStatusService.getLeaveStatusByStatus(PENDING
                    .toString()));
      } else {
        throw new EntityAlreadyExistsException(
            ErrorMessageConstants.LEAVE_WITH_GIVEN_TYPE_AND_STATUS_EXISTS);
      }

      return employeeLeaveApplicationResponseBuilder
          .buildFromResult(employeeLeaveApplicationRepository.save(leaveToApply));
    }

  }

  public EmployeeLeaveApplicationResponse updateLeaveApplication(
      final Long id, final EmployeeLeaveApplicationUpdateRequest employeeLeaveApplicationUpdateRequest)
      throws EntityNotFoundException {

    Optional<Employee> employee = employeeServiceClient
        .getEmployeeById(employeeLeaveApplicationUpdateRequest.getEmployeeId());

    if (!employee.isPresent()) {
      throw new EmployeeServiceNotAvailableException(ErrorMessageConstants.EMPLOYEE_SERVICE_NOT_AVAILABLE);
    }
    LeaveStatus leaveStatus = leaveStatusService
        .getLeaveStatusById(employeeLeaveApplicationUpdateRequest.getLeaveStatusId());

    EmployeeLeaveApplication employeeLeaveApplicationToUpdate = employeeLeaveApplicationRepository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.LEAVE_BY_ID_NOT_FOUND));

    EmployeeLeaveApplication employeeUpdatedLeaveApplication = employeeLeaveApplicationBuilder
        .buildFromRequest(employeeLeaveApplicationToUpdate, leaveStatus);

    employeeUpdatedLeaveApplication = employeeLeaveApplicationRepository.save(employeeUpdatedLeaveApplication);

    return employeeLeaveApplicationResponseBuilder.buildFromResult(employeeUpdatedLeaveApplication);
  }

  private int getNoOfDaysLeaveApplied(final LocalDate startDate, final LocalDate endDate) {
    return (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;
  }

  private void isStartAndEndDateAreOverlappingWithExistingRecordForGivenEmployeeId(
      final Long employeeId, final LocalDate startDate, final LocalDate endDate)
      throws EntityAlreadyExistsException {
    List<EmployeeLeaveApplication> employeeLeaveApplicationList = employeeLeaveApplicationRepository
        .findLeaveAlreadyAppliedInTheGivenDateRange(employeeId, startDate, endDate);

    if (!employeeLeaveApplicationList.isEmpty()) {

      throw new EntityAlreadyExistsException(
          ErrorMessageConstants.LEAVE_APPLICATION_WITH_GIVEN_LEAVE_TYPE_AND_START_DATE_AND_END_DATE_OVERLAPPING);
    }
  }
}