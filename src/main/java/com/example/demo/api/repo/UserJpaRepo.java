package com.example.demo.api.repo;

import com.example.demo.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepo extends JpaRepository<User, Long> {
}
