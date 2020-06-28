package com.niks.oauth2authenticationservice.models.db;

import com.niks.oauth2authenticationservice.enums.ERole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "roles")
public class Role extends BaseModel {

  @Enumerated(EnumType.STRING)
  @Column(name = "name", nullable = false, length = 20)
  private ERole name;
}