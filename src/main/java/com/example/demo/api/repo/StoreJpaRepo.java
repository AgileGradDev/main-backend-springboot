package com.example.demo.api.repo;

import com.example.demo.api.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface StoreJpaRepo extends JpaRepository<Store, Long> {
    Store findByName(String name);

    @Transactional
    void deleteByname(String name);
}
