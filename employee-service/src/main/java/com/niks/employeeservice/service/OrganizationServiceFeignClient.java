package com.niks.employeeservice.service;

import com.niks.employeeservice.constants.ErrorMessageConstants;
import com.niks.employeeservice.model.db.Organization;
import com.niks.employeeservice.service.exception.EntityNotFoundException;
import feign.FeignException;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${organization.service.name}", fallbackFactory = OrganizationsFallbackFactory.class)
public interface OrganizationServiceFeignClient {

  @GetMapping(value = "/api/organizations/{organizationId}")
  public Organization getOrganizationById(@PathVariable Long organizationId);

}

@Component
class OrganizationsFallbackFactory implements FallbackFactory<OrganizationServiceFeignClient> {

  @Override
  public OrganizationServiceFeignClient create(Throwable cause) {
    return new OrganizationServiceClientFallback(cause);
  }
}

class OrganizationServiceClientFallback implements OrganizationServiceFeignClient{
  Logger logger = LoggerFactory.getLogger(this.getClass());
  private final Throwable cause;

  public OrganizationServiceClientFallback(Throwable cause) {
    this.cause = cause;
  }
  @Override
  public Organization getOrganizationById(Long organizationId){
    if (cause instanceof FeignException && ((FeignException) cause).status() == 404) {
      logger.error("404 error took place when getOrganizationById was called with organizationId: "
          + organizationId +  ". Error message: "
          + cause.getLocalizedMessage());
      throw new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_BY_ID_NOT_FOUND);
    } else {
      logger.error("Other error took place: " + cause.getLocalizedMessage());
    }
    return new Organization();
  }
}