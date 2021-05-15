package com.agd.backend.api.repository;

import com.agd.backend.api.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface StoreJpaRepository extends JpaRepository<Store, UUID> {
    Store findByName(String name);

    @Transactional
    void deleteByName(String name);
}
