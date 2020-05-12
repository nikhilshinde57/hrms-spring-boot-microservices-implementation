package com.niks.leaveservice.repository;

import com.niks.leaveservice.model.db.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface LeaveStatusRepository extends JpaRepository<LeaveStatus, Long> {

  public LeaveStatus findByStatus(@Param("status") String status);

}