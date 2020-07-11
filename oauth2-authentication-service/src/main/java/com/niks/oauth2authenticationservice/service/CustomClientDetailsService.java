package com.niks.oauth2authenticationservice.service;

import com.niks.oauth2authenticationservice.config.PasswordEncoderConfig;
import com.niks.oauth2authenticationservice.constants.ErrorMessageConstants;
import com.niks.oauth2authenticationservice.models.db.CustomClientDetails;
import com.niks.oauth2authenticationservice.repository.CustomClientDetailsRepository;
import com.niks.oauth2authenticationservice.service.exception.CustomClientRegistrationException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomClientDetailsService implements ClientDetailsService {

  @Autowired
  PasswordEncoderConfig getPasswordEncoder;

  @Autowired
  private CustomClientDetailsRepository customClientDetailsRepository;

  @Override
  public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
    Optional<CustomClientDetails> client = customClientDetailsRepository.findByClientId(clientId);

    if(client.isPresent()){
      String resourceIds = client.get().getResourceIds();
      String scopes = client.get().getScopes();
      String grantTypes = client.get().getAuthorizedGrantTypes();
      String authorities = client.get().getAuthorities();

      BaseClientDetails base = new BaseClientDetails(client.get().getClientId(), resourceIds, scopes, grantTypes, authorities);
      base.setClientSecret(getPasswordEncoder.getPasswordEncoder().encode(client.get().getClientSecret()));
      base.setAccessTokenValiditySeconds(client.get().getAccessTokenValiditySeconds());
      base.setRefreshTokenValiditySeconds(client.get().getRefreshTokenValiditySeconds());
      Collection<String> scopesCollection = Arrays.asList(client.get().getScopes().split(","));
      base.setAutoApproveScopes(scopesCollection);
      return base;
    }
    else {
      throw new CustomClientRegistrationException(ErrorMessageConstants.CLIENT_NOT_REGISTERED);
    }
  }
}
