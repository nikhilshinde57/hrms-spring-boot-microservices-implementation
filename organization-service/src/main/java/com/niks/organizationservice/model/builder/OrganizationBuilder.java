package com.niks.organizationservice.model.builder;

import com.niks.organizationservice.model.db.Organization;
import com.niks.organizationservice.request.organization.OrganizationCreateRequest;
import com.niks.organizationservice.request.organization.OrganizationUpdateRequest;
import java.util.UUID;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class OrganizationBuilder {

  public Organization buildFromRequest(OrganizationCreateRequest organizationCreateRequest) {
    Organization organization = new Organization();
    organization.setName(organizationCreateRequest.getName());
    organization.setGuid(UUID.randomUUID());
    organization.setIsActive(
        organizationCreateRequest.getIsActive() != null ? organizationCreateRequest.getIsActive()
            : Boolean.TRUE);
    return organization;
  }

  public Organization buildFromRequest(Organization organizationToBeUpdated,
      OrganizationUpdateRequest organizationUpdateRequest) {
    Organization organizationAfterUpdate = new Organization(organizationToBeUpdated);
    if (organizationUpdateRequest.getName() != null && StringUtils
        .isNotBlank(organizationUpdateRequest.getName())) {
      organizationAfterUpdate.setName(organizationUpdateRequest.getName());
    }
    if (organizationUpdateRequest.getIsActive() != null) {
      organizationAfterUpdate.setIsActive(organizationUpdateRequest.getIsActive());
    }
    return organizationAfterUpdate;
  }
}