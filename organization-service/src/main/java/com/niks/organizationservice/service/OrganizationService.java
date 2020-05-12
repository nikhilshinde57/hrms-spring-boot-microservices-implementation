package com.niks.organizationservice.service;

import com.niks.organizationservice.constants.ErrorMessageConstants;
import com.niks.organizationservice.model.builder.OrganizationBuilder;
import com.niks.organizationservice.model.db.Organization;
import com.niks.organizationservice.repository.OrganizationRepository;
import com.niks.organizationservice.request.organization.OrganizationCreateRequest;
import com.niks.organizationservice.request.organization.OrganizationUpdateRequest;
import com.niks.organizationservice.service.exception.EntityAlreadyExistsException;
import com.niks.organizationservice.service.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

  @Autowired
  OrganizationRepository organizationRepository;

  @Autowired
  OrganizationBuilder organizationBuilder;

  public Organization getOrganizationById(Long organizationId) throws EntityNotFoundException {
    return organizationRepository.findById(organizationId)
        .orElseThrow(
            () -> new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_BY_ID_NOT_FOUND));
  }

  public Organization createOrganization(OrganizationCreateRequest organizationCreateRequest)
      throws EntityAlreadyExistsException {
    List<Organization> organizations = organizationRepository
        .findByName(organizationCreateRequest.getName());
    if (organizations.isEmpty()) {
      Organization organizationToCreate = organizationBuilder
          .buildFromRequest(organizationCreateRequest);

      return organizationRepository.save(organizationToCreate);
    } else {
      throw new EntityAlreadyExistsException(ErrorMessageConstants.ORGANIZATION_DUPLICATE_NAME);
    }
  }

  public Organization updateOrganization(Long organizationId,
      OrganizationUpdateRequest organizationUpdateRequest)
      throws EntityNotFoundException {
    Organization organizationToUpdate = organizationRepository
        .findById(organizationId)
        .orElseThrow(() -> new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_BY_ID_NOT_FOUND));

    Organization updatedOrganization = organizationBuilder
        .buildFromRequest(organizationToUpdate, organizationUpdateRequest);
    updatedOrganization = organizationRepository.save(updatedOrganization);
    return updatedOrganization;
  }

  public void deleteOrganizationById(Long id) throws EntityNotFoundException {
    Optional<Organization> organizationToDelete = organizationRepository.findById(id);

    if (organizationToDelete.isPresent()) {
      organizationRepository.deleteById(id);
    } else {
      throw new EntityNotFoundException(ErrorMessageConstants.ORGANIZATION_BY_ID_NOT_FOUND);
    }
  }

  public List<Organization> getAllOrganizations() {
    return organizationRepository.findAll();
  }
}
