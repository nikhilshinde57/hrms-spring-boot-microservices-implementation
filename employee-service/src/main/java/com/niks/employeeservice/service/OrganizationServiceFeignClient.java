package com.niks.employeeservice.service;

import com.niks.employeeservice.model.db.Organization;
import com.niks.employeeservice.service.exception.ServiceException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${organization.service.name}")
public interface OrganizationServiceFeignClient {

  @GetMapping(value = "/hrms/api/organizations/{organizationId}")
  public Organization getOrganizationById(@PathVariable Long organizationId) throws ServiceException;

}