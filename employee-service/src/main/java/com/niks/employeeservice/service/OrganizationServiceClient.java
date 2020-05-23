package com.niks.employeeservice.service;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.niks.employeeservice.constants.ErrorMessageConstants;
import com.niks.employeeservice.model.db.Organization;
import com.niks.employeeservice.service.exception.EntityNotFoundException;
import com.niks.employeeservice.service.exception.OrganizationServiceException;
import com.niks.employeeservice.service.exception.OrganizationServiceNotAvailableException;
import feign.FeignException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrganizationServiceClient {

  @Autowired
  OrganizationServiceFeignClient organizationServiceFeignClient;

  public Optional<Organization> getOrganizationById(Long organizationId) {
    try {
      log.info(String
          .format("Calling getOrganizationById method of Organization service with organizationId as %s",
              organizationId));
      Organization organization = this.organizationServiceFeignClient.getOrganizationById(organizationId);
      log.info(String
          .format("Call completed for getOrganizationById method of Organization service with organizationId as %s",
              organizationId));
      return Optional.ofNullable(organization);
    } catch (HystrixRuntimeException ex) {
      log.error(String.format(
          "Exception occurred while calling getOrganizationById method of Organization service with organizationId as %s",
          organizationId),
          ex);
      if ((ex.getCause() instanceof FeignException) && ((FeignException) ex.getCause()).status() == 404) {
        throw new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_BY_ID_NOT_FOUND);
      } else if ((ex.getCause() instanceof FeignException) && ((FeignException) ex.getCause()).status() == 400) {
        throw new OrganizationServiceException(ErrorMessageConstants.ORGANIZATION_ID_IS_INVALID);
      } else {
        throw new OrganizationServiceNotAvailableException(ErrorMessageConstants.ORGANIZATION_SERVICE_NOT_AVAILABLE);
      }
    } catch (Exception ex) {
      log.error(String.format(
          "Exception occurred while calling getOrganizationById method of Organization service with organizationId as %s",
          organizationId),
          ex);
      throw new OrganizationServiceException(ErrorMessageConstants.ORGANIZATION_SERVICE_ERROR);
    }
  }
}
