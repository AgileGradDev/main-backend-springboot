package com.agd.backend.api.repo;

import com.agd.backend.api.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface StoreJpaRepo extends JpaRepository<Store, Long> {
    Store findByName(String name);

    @Transactional
    void deleteByName(String name);
}
