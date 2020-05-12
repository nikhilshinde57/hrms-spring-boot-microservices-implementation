package com.niks.leaveservice.service;

import com.niks.leaveservice.constants.ErrorMessageConstants;
import com.niks.leaveservice.model.db.LeaveStatus;
import com.niks.leaveservice.repository.LeaveStatusRepository;
import com.niks.leaveservice.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LeaveStatusService {

  @Autowired
  LeaveStatusRepository leaveStatusRepository;

  public LeaveStatus getLeaveStatusById(Long id) throws EntityNotFoundException {
    return leaveStatusRepository.findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorMessageConstants.LEAVE_STATUS_BY_ID_NOT_FOUND));
  }

  public LeaveStatus getLeaveStatusByStatus(String status) throws EntityNotFoundException {
    LeaveStatus leaveStatuse = leaveStatusRepository.findByStatus(status);

    if (leaveStatuse != null) {
      return leaveStatuse;
    } else {
      throw new EntityNotFoundException(ErrorMessageConstants.LEAVE_STATUS_BY_STATUS_NAME_NOT_FOUND);
    }
  }
}