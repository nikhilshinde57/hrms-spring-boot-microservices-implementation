package com.niks.leaveservice.service.client.feign;

import com.niks.leaveservice.model.db.Organization;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${organization.service.name}")
public interface OrganizationServiceFeignClient {

  @GetMapping(value = "/api/organizations/{organizationId}")
  public Organization getOrganizationById(@PathVariable Long organizationId);
}