package com.niks.leaveservice.repository;

import com.niks.leaveservice.model.db.EmployeeLeaveApplication;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeLeaveApplicationRepository extends JpaRepository<EmployeeLeaveApplication, Long> {

  public EmployeeLeaveApplication findByEmployeeIdAndStartDateAndEndDate(
      @Param("employee_id") final Long employeeId,
      @Param("start_date") final LocalDate startDate,
      @Param("end_date") final LocalDate endDate
  );

  public List<EmployeeLeaveApplication> findByEmployeeId(@Param("employee_id") final Long employeeId);

  public List<EmployeeLeaveApplication> findByEmployeeIdAndLeaveTypeId(
      @Param("employee_id") final Long employeeId,
      @Param("leave_type_id") final Long leaveTypeId);

  public List<EmployeeLeaveApplication> findByEmployeeIdAndLeaveTypeIdAndLeaveStatusId(
      @Param("employee_id") final Long employeeId,
      @Param("leave_type_id") final Long leaveTypeId,
      @Param("leave_status_id") final Long leaveStatusId
  );

  public List<EmployeeLeaveApplication> findByEmployeeIdAndLeaveTypeIdAndLeaveStatusIdNotIn(
      @Param("employee_id") final Long employeeId,
      @Param("leave_type_id") final Long leaveTypeId,
      @Param("leave_status_id") final Collection<Long> leaveStatusIds
  );

  @Query
      (" SELECT l " +
          " FROM EmployeeLeaveApplication l " +
          " WHERE  l.employeeId = :employeeId" +
          " AND (" +
          "       (:startDate <= l.startDate AND l.startDate <=  :endDate)" +
          "       OR (:startDate <= l.endDate AND l.endDate <=  :endDate)" +
          "       OR (l.startDate <= :startDate AND  :endDate <= l.endDate )" +
          " )")
  public List<EmployeeLeaveApplication> findLeaveAlreadyAppliedInTheGivenDateRange(
      @Param("employeeId") Long employeeId,
      @Param("startDate") LocalDate startDate,
      @Param("endDate") LocalDate endDate);
}