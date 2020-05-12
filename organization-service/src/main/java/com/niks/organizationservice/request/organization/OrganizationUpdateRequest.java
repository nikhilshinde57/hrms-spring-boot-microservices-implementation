package com.niks.organizationservice.request.organization;

import com.niks.organizationservice.constants.ErrorMessageConstants;
import javax.validation.constraints.AssertTrue;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

@Data
public class OrganizationUpdateRequest {

  private String name;

  private Boolean isActive;

  @AssertTrue(message = ErrorMessageConstants.ORGANIZATION_NAME_NOT_BLANK)
  private boolean isValidName() {
    return name == null || StringUtils.isNoneBlank(name);
  }
}
