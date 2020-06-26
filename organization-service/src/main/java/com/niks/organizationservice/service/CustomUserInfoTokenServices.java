package com.niks.organizationservice.service;

import com.niks.organizationservice.constants.ErrorMessageConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.FixedAuthoritiesExtractor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.BaseOAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

public class CustomUserInfoTokenServices implements ResourceServerTokenServices {

  public static final Logger LOGGER = LoggerFactory.getLogger(CustomUserInfoTokenServices.class);
  private final String userInfoEndpointUrl;
  private final String clientId;
  private OAuth2RestOperations restTemplate;
  private String tokenType = DefaultOAuth2AccessToken.BEARER_TYPE;
  private AuthoritiesExtractor authoritiesExtractor = new FixedAuthoritiesExtractor();
  private static final String ERROR = "error";
  private static final String UNKNOWN = "unknown";
  private static final String CLIENT_ID = "clientId";
  private static final String SCOPE = "scope";
  private static final String CREDENTIALS = "N/A";

  private static final String[] PRINCIPAL_KEYS = new String[]{"user", "username",
      "userid", "user_id", "login", "id", "name"};

  public CustomUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
    this.userInfoEndpointUrl = userInfoEndpointUrl;
    this.clientId = clientId;
  }

  @Override
  public OAuth2Authentication loadAuthentication(String accessToken)
      throws AuthenticationException, InvalidTokenException {
    Map<String, Object> map = getMap(this.userInfoEndpointUrl, accessToken);

    if (map.containsKey(ERROR)) {
      LOGGER.debug("userinfo returned error: " + map.get("error"));
      throw new InvalidTokenException(accessToken);
    }
    return extractAuthentication(map);
  }

  private OAuth2Authentication extractAuthentication(Map<String, Object> map) {

    Object principal = getPrincipal(map);
    OAuth2Request request = getRequest(map);
    List<GrantedAuthority> authorities = this.authoritiesExtractor
        .extractAuthorities(map);
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
        principal,CREDENTIALS , authorities);
    token.setDetails(map);
    return new OAuth2Authentication(request, token);
  }

  private Object getPrincipal(Map<String, Object> map) {
    for (String key : PRINCIPAL_KEYS) {
      if (map.containsKey(key)) {
        return map.get(key);
      }
    }
    return UNKNOWN;
  }

  private OAuth2Request getRequest(Map<String, Object> map) {

    String clientId = (String) map.get(CLIENT_ID);
    Set<String> scope = new LinkedHashSet<>(map.containsKey(SCOPE) ?
        (Collection<String>) map.get(SCOPE) : Collections.<String>emptySet());
    return new OAuth2Request(null, clientId, null, true, new HashSet<>(scope),
        null, null, null, null);
  }

  private Map<String, Object> getMap(String path, String accessToken) {

    LOGGER.info("Getting user info from: " + path);
    try {
      OAuth2RestOperations restTemplate = this.restTemplate;
      if (restTemplate == null) {
        BaseOAuth2ProtectedResourceDetails resource = new BaseOAuth2ProtectedResourceDetails();
        resource.setClientId(this.clientId);
        restTemplate = new OAuth2RestTemplate(resource);
      }

      OAuth2AccessToken existingToken = restTemplate.getOAuth2ClientContext()
          .getAccessToken();
      if (existingToken == null || !accessToken.equals(existingToken.getValue())) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(
            accessToken);
        token.setTokenType(this.tokenType);
        restTemplate.getOAuth2ClientContext().setAccessToken(token);
      }
      return restTemplate.getForEntity(path, Map.class).getBody();
    } catch (Exception ex) {
      LOGGER.info(ErrorMessageConstants.OAUTH_FAILED_TO_FETCH_USER +": " + ex.getClass() + ", "
          + ex.getMessage());
      return Collections.<String, Object>singletonMap(ERROR,
          ErrorMessageConstants.OAUTH_FAILED_TO_FETCH_USER);
    }
  }

  @Override
  public OAuth2AccessToken readAccessToken(String s) {
    throw new UnsupportedOperationException(ErrorMessageConstants.OAUTH_NOT_SUPPORTED_OPERATION);
  }
}
