package com.niks.employeeservice.repository;

import com.niks.employeeservice.model.db.Employee;
import com.niks.employeeservice.request.employee.EmployeeSearchRequest;
import java.util.List;

public interface EmployeeRepositoryCustom {

  List<Employee> searchEmployee(final EmployeeSearchRequest employeeSearchRequest);
}
