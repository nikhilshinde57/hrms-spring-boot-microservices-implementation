package com.niks.authenticationservice.repository;

import com.niks.authenticationservice.model.db.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUserName(String username);

  Boolean existsByUserName(String username);

  Boolean existsByEmail(String email);
}