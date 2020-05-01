package com.niks.employeeservice.service;

import com.niks.employeeservice.model.db.Organization;
import com.niks.employeeservice.service.exception.EntityNotFoundException;
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

  public Organization getOrganizationById(Long organizationId) throws EntityNotFoundException {
      return this.organizationServiceFeignClient.getOrganizationById(organizationId);
  }
}
