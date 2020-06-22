package com.niks.oauth2authenticationservice.repository;

import com.niks.oauth2authenticationservice.models.ERole;
import com.niks.oauth2authenticationservice.models.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);
}

