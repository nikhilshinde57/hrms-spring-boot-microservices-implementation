package com.niks.leaveservice.service.client.feign;

import com.niks.leaveservice.model.db.Employee;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${employee.service.name}")
public interface EmployeeServiceFeignClient {

  @GetMapping(value = "/api/employees/{id}")
  public Employee getEmployeeById(@PathVariable Long id);
}