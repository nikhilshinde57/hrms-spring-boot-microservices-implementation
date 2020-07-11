package com.niks.employeeservice.service;

import com.niks.employeeservice.model.db.Organization;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${organization.service.name}")
public interface OrganizationServiceFeignClient {

  @GetMapping(value = "/api/organizations/{organizationId}")
  @Headers("Content-Type: application/json")
  public Organization getOrganizationById(@PathVariable Long organizationId);
}