package com.example.demo.api.repo;

import com.example.demo.api.entity.Good;
import com.example.demo.api.entity.Store;
import com.example.demo.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodJpaRepo extends JpaRepository<Good, Long> {
    List<Good> findByStore(Store store);

    List<Good> findByUser(User user);

    Optional<Good> findByUserAndStore(User user, Store store);

    int countByStore(Store store);
}
