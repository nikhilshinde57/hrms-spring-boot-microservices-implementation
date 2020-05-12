package com.niks.leaveservice.repository;

import com.niks.leaveservice.model.db.EmployeeLeaveAllocation;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLeaveAllocationRepository  extends
    JpaRepository<EmployeeLeaveAllocation, Long> {

  public List<EmployeeLeaveAllocation> findByEmployeeId(@Param("employee_id") final Long employee_id);

  public List<EmployeeLeaveAllocation> findByEmployeeIdAndLeaveTypeIdAndStartDateAndEndDate(
      @Param("employee_id") final Long employeeId,
      @Param("leave_type_id") final Long leaveTypeId,
      @Param("start_date") final Date startDate,
      @Param("end_date") final Date endDate
  );

  public EmployeeLeaveAllocation findByEmployeeIdAndLeaveTypeId(
      @Param("employee_id") final Long employeeId,
      @Param("leave_type_id") final Long leaveTypeId
  );

  public void deleteByEmployeeId(@Param("employee_id") final Long employeeId);

  public void deleteByEmployeeIdAndLeaveTypeId(@Param("employee_id") final Long employeeId,
      @Param("leave_type_id") final Long leaveTypeId);

  @Query
      (" SELECT l " +
          " FROM EmployeeLeaveAllocation l " +
          " WHERE l.employeeId = :employeeId" +
          " AND l.employeeId = :leaveTypeId" +
          " AND (" +
          "       (:startDate <= l.startDate AND l.startDate <=  :endDate)" +
          "       OR (:startDate <= l.endDate AND l.endDate <=  :endDate)" +
          "       OR (l.startDate <= :startDate AND  :endDate <= l.endDate )" +
          " )")
  public List<EmployeeLeaveAllocation> findGivenTypeOfLeaveAllocatedOrNotInTheGivenDateRange(
      @Param("employeeId") Long employeeId,
      @Param("leaveTypeId") Long leaveTypeId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}