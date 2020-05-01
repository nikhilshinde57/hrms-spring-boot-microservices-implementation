package com.niks.employeeservice.request.employee;

import com.niks.employeeservice.constants.ErrorMessageConstants;
import java.util.Date;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class EmployeeUpdateRequest {

  private String empId;

  private Long organizationId;

  private String firstName;

  private String middleName;

  private String lastName;

  private Date dateOfBirth;

  private Boolean isActive;

  @Email(message = ErrorMessageConstants.EMPLOYEE_EMAIL_ID_NOT_PROPER)
  private String email;

  private Date joiningDate;

  private Date exitDate;

  private String address;

  private String contact;

  private String designation;

  private Long reportsTo;

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_EMP_ID_NOT_NULL)
  private boolean isValidEmployeeId() {
    return empId == null;
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_ORGANIZATION_ID_NOT_NULL)
  private boolean isValidOrganizationId() {
    return organizationId == null;
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_FIRST_NAME_NOT_BLANK)
  private boolean isValidFirstName() {
    return firstName == null || StringUtils.isNoneBlank(firstName);
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_LAST_NAME_NOT_BLANK)
  private boolean isValidLastName() {
    return lastName == null || StringUtils.isNoneBlank(lastName);
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_MIDDLE_NAME_NOT_BLANK)
  private boolean isValidMiddleName() {
    return middleName == null || StringUtils.isNoneBlank(middleName);
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_DATE_OF_BIRTH_NOT_NULL)
  private boolean isValidDateOfBirth() {
    return dateOfBirth == null;
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_INVALID_STATAUS)
  private boolean isValidStatus() {
    return isActive == null || isActive || !isActive;
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_DATE_OF_JOINING_NOT_NULL)
  private boolean isValidDateOfJoining() {
    return joiningDate == null;
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_DATE_OF_EXIT_NOT_NULL)
  private boolean isValidDateOfExit() {
    return exitDate == null;
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_ADDRESS_NOT_NULL)
  private boolean isValidAddress() {
    return address == null || StringUtils.isNoneBlank(middleName);
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_CONTACT_NOT_NULL)
  private boolean isValidContact() {
    return contact == null || StringUtils.isNoneBlank(middleName);
  }

  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_DESIGNATION_NOT_NULL)
  private boolean isValidDesignation() {
    return designation == null || StringUtils.isNoneBlank(middleName);
  }

}
