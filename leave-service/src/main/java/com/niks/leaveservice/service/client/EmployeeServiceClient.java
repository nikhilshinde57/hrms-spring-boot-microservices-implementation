package com.niks.leaveservice.service.client;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.niks.leaveservice.constants.ErrorMessageConstants;
import com.niks.leaveservice.model.db.Employee;
import com.niks.leaveservice.service.client.feign.EmployeeServiceFeignClient;
import com.niks.leaveservice.service.exception.EntityNotFoundException;
import com.niks.leaveservice.service.exception.OrganizationServiceException;
import com.niks.leaveservice.service.exception.OrganizationServiceNotAvailableException;
import feign.FeignException;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EmployeeServiceClient {

  @Autowired
  EmployeeServiceFeignClient employeeServiceFeignClient;

  public Optional<Employee> getEmployeeById(Long employeeId) {
    try {
      log.info(String
          .format("Calling getEmployeeById method of Employee service with employeeId as %s",
              employeeId));
      Employee employee = employeeServiceFeignClient.getEmployeeById(employeeId);
      log.info(String
          .format("Call completed for getEmployeeById method of Employee service with employeeId as %s",
              employeeId));
      return Optional.ofNullable(employee);
    } catch (HystrixRuntimeException ex) {
      log.error(String.format(
          "Exception occurred while calling getEmployeeById method of Employee service with employeeId as %s",
          employeeId),
          ex);
      if ((ex.getCause() instanceof FeignException) && ((FeignException) ex.getCause()).status() == 404) {
        throw new EntityNotFoundException(ErrorMessageConstants.EMPLOYEE_BY_ID_NOT_FOUND);
      } else if ((ex.getCause() instanceof FeignException) && ((FeignException) ex.getCause()).status() == 400) {
        throw new OrganizationServiceException(ErrorMessageConstants.EMPLOYEE_ID_IS_INVALID);
      } else {
        throw new OrganizationServiceNotAvailableException(ErrorMessageConstants.EMPLOYEE_SERVICE_NOT_AVAILABLE);
      }
    } catch (Exception ex) {
      log.error(String.format(
          "Exception occurred while calling getEmployeeById method of Employee service with employeeId as %s",
          employeeId),
          ex);
      throw new OrganizationServiceException(ErrorMessageConstants.EMPLOYEE_SERVICE_ERROR);
    }
  }
}