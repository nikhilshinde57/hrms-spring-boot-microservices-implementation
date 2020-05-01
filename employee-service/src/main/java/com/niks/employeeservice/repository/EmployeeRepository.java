package com.niks.employeeservice.repository;

import com.niks.employeeservice.model.db.Employee;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, EmployeeRepositoryCustom {

  List<Employee> findByEmail(@Param("email") String email);

  Employee findByEmpId(@Param("emp_id") Long empId);

  boolean existsByIdAndOrganizationId(@Param("id") final Long id,
      @Param("organization_id") final Long organizationId);

  Employee findByEmpId(@Param("emp_id") String empId);

  List<Employee> findByEmpIdOrEmail(@Param("emp_id") String empId,
      @Param("email") String email);
}
