package com.niks.oauth2authenticationservice.models.db;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@JsonInclude(Include.NON_NULL)
@Table(name = "client_details")
@NoArgsConstructor
public class CustomClientDetails extends BaseModel {

  private static final long serialVersionUID = 1L;

  @Column(name = "client_id", nullable = false, unique = true, length = 100)
  private String clientId;
  @Column(name = "client_secret", nullable = false, unique = true, length = 100)
  private String clientSecret;
  @Column(name = "resource_ids", length = 100)
  private String resourceIds;
  @Column(name = "secret_required")
  private boolean secretRequired;
  @Column(name = "scoped")
  private boolean scoped;
  @Column(name = "scopes", length = 100)
  private String scopes;
  @Column(name = "authorized_grant_types", length = 100)
  private String authorizedGrantTypes;
  @Column(name = "registered_redirect_uris", length = 100)
  private String registeredRedirectUris;
  @Column(name = "authorities", length = 100)
  private String authorities;
  @Column(name = "access_token_validity_seconds")
  private Integer accessTokenValiditySeconds;
  @Column(name = "refresh_token_validity_seconds")
  private Integer refreshTokenValiditySeconds;

}
