package com.niks.leaveservice.model.db;

import javax.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class Organization extends BaseModel {

  @Column(name = "name", nullable = false, unique = true, length = 200)
  private String name;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  public Organization() {

  }

  public Organization(final Organization organization) {
    this.setId(organization.getId());
    this.setName(organization.getName());
    this.setGuid(organization.getGuid());
    this.setIsActive(organization.getIsActive());
    this.setCreatedAt(organization.getCreatedAt());
    this.setCreatedBy(organization.getCreatedBy());
  }
}
