package com.niks.leaveservice.repository;

import com.niks.leaveservice.model.db.OrganizationLeaveAllocation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizationLeaveAllocationRepository extends JpaRepository<OrganizationLeaveAllocation, Long> {

  List<OrganizationLeaveAllocation> findByOrganizationId(@Param("org_id") Long organizationId);

  List<OrganizationLeaveAllocation> findByOrganizationIdAndLeaveTypeId(@Param("org_id") Long organizationId,
      @Param("leave_type_id") Long leaveTypeId);

}
