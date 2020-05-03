package com.niks.employeeservice.service;

import com.niks.employeeservice.model.db.Organization;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class OrganizationServiceClient {


  private final RestTemplate restTemplate;

  private final OrganizationServiceFeignClient organizationServiceFeignClient;

  @Value("${organization.service.base.url}")
  private String INVENTORY_API_PATH;

  @Autowired
  OrganizationServiceClient(RestTemplate restTemplate,OrganizationServiceFeignClient organizationServiceFeignClient){
    this.restTemplate = restTemplate;
    this.organizationServiceFeignClient = organizationServiceFeignClient;
  }

  public Optional<Organization> getOrganizationById(Long organizationId) {
      Organization organization = this.organizationServiceFeignClient.getOrganizationById(organizationId);
      return Optional.ofNullable(organization);
  }
}
