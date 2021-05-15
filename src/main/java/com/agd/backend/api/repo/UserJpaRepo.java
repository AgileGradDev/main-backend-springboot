package com.agd.backend.api.repo;

import com.agd.backend.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJpaRepo extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndProvider(String email, String provider);
}