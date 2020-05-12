package com.niks.employeeservice.request.employee;

import com.niks.employeeservice.constants.ErrorMessageConstants;
import java.util.Date;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EmployeeCreateRequest {

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_EMP_ID_NOT_NULL)
  private String empId;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_ORGANIZATION_ID_NOT_NULL)
  private Long organizationId;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_FIRST_NAME_NOT_NULL)
  @NotBlank(message = ErrorMessageConstants.EMPLOYEE_FIRST_NAME_NOT_BLANK)
  private String firstName;

  private String middleName;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_LAST_NAME_NOT_NULL)
  @NotBlank(message = ErrorMessageConstants.EMPLOYEE_LAST_NAME_NOT_BLANK)
  private String lastName;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_DATE_OF_BIRTH_NOT_NULL)
  private Date dateOfBirth;

  private Boolean isActive;

  @Email(message = ErrorMessageConstants.EMPLOYEE_EMAIL_ID_NOT_PROPER)
  @NotNull(message = ErrorMessageConstants.EMPLOYEE_EMAIL_NOT_NULL)
  @NotBlank(message = ErrorMessageConstants.EMPLOYEE_EMAIL_NOT_BLANK)
  private String email;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_DATE_OF_JOINING_NOT_NULL)
  private Date joiningDate;

  private Date exitDate;

  private String address;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_CONTACT_NOT_NULL)
  @NotBlank(message = ErrorMessageConstants.EMPLOYEE_CONTACT_NOT_BLANK)
  private String contact;

  @NotNull(message = ErrorMessageConstants.EMPLOYEE_DESIGNATION_NOT_NULL)
  @NotBlank(message = ErrorMessageConstants.EMPLOYEE_DESIGNATION_NOT_BLANK)
  private String designation;

  private Long reportsTo;


  @AssertTrue(message = ErrorMessageConstants.EMPLOYEE_INVALID_STATAUS)
  private boolean isValidStatus() {
    if (isActive == null) {
      return true;
    }
    return isActive || !isActive;
  }
}
