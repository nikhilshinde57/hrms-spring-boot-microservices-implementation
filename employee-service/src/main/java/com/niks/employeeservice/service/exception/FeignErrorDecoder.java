package com.niks.employeeservice.service.exception;

import com.niks.employeeservice.constants.ErrorMessageConstants;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

//@Component
//public class FeignErrorDecoder implements ErrorDecoder {
//
//  Logger logger = LoggerFactory.getLogger(this.getClass());
//
//  @Override
//  public Exception decode(String s, Response response) {
//    switch (response.status()){
//      case 400:
//        logger.error("Status code " + response.status() + ", methodKey = ");
//      case 404:
//      {
//        return new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_BY_ID_NOT_FOUND);
//      }
//      default:
//        return new Exception(response.reason());
//    }
//  }
//}