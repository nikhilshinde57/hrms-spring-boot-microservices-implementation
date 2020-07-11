package com.niks.oauth2authenticationservice.repository;

import com.niks.oauth2authenticationservice.models.db.CustomClientDetails;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomClientDetailsRepository  extends JpaRepository<CustomClientDetails, Long>{

  Optional<CustomClientDetails> findByClientId(String clientId);
}
