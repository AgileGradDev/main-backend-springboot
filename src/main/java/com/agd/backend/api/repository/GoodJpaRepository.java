package com.agd.backend.api.repository;

import com.agd.backend.api.entity.Good;
import com.agd.backend.api.entity.Store;
import com.agd.backend.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoodJpaRepository extends JpaRepository<Good, Long> {
    List<Good> findByStore(Store store);

    List<Good> findByUser(User user);

    Optional<Good> findByUserAndStore(User user, Store store);

    int countByStore(Store store);
}
