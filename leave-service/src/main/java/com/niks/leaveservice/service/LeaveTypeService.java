package com.niks.leaveservice.service;

import com.niks.leaveservice.constants.ErrorMessageConstants;
import com.niks.leaveservice.model.db.LeaveType;
import com.niks.leaveservice.repository.LeaveTypeRepository;
import com.niks.leaveservice.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveTypeService {

  @Autowired
  LeaveTypeRepository leaveTypeRepository;

  public LeaveType getLeaveTypeById(Long id) throws EntityNotFoundException {
    return leaveTypeRepository.findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorMessageConstants.LEAVE_TYPE_BY_ID_NOT_FOUND));
  }
}