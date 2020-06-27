package com.niks.organizationservice.controller;

import com.niks.organizationservice.service.CustomUserInfoTokenServices;
import com.niks.organizationservice.service.exception.EntityAlreadyExistsException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.niks.organizationservice.model.db.Organization;
import com.niks.organizationservice.request.organization.OrganizationCreateRequest;
import com.niks.organizationservice.request.organization.OrganizationUpdateRequest;
import com.niks.organizationservice.service.OrganizationService;
import com.niks.organizationservice.service.exception.ServiceException;

@RefreshScope
@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

  @Autowired
  OrganizationService organizationService;

  @Autowired
  ResourceServerProperties resourceServerProperties;

  @Bean
  @Primary
  public ResourceServerTokenServices myUserInfoTokenServices() {
    return new CustomUserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
  }

  @GetMapping(value = "/{organizationId}")
  @RolesAllowed({ "ROLE_USER","ROLE_TL", "ROLE_ADMIN"})
  public Organization getOrganizationById(@PathVariable Long organizationId)
      throws ServiceException {
    return organizationService.getOrganizationById(organizationId);
  }

  @PostMapping(value = "")
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public Organization createOrganization(
      @Valid @RequestBody @NotNull OrganizationCreateRequest organizationCreateRequest)
      throws EntityAlreadyExistsException {
    return organizationService.createOrganization(organizationCreateRequest);
  }

  @PatchMapping(value = "/{organizationId}")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public Organization updateOrganizationById(
      @PathVariable Long organizationId,
      @NotNull @Valid @RequestBody OrganizationUpdateRequest organizationUpdateRequest)
      throws ServiceException {
    return organizationService.updateOrganization(organizationId, organizationUpdateRequest);
  }

  @DeleteMapping(value = "{organizationId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public void deleteOrganizationById(@PathVariable Long organizationId) throws ServiceException {
    organizationService.deleteOrganizationById(organizationId);
  }

  @GetMapping(value = "")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public List<Organization> getAllOrganizations() {
    return organizationService.getAllOrganizations();
  }
}
