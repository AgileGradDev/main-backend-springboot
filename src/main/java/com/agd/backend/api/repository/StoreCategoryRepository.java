package com.agd.backend.api.repository;

import com.agd.backend.api.entity.StoreCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

public interface StoreCategoryRepository extends JpaRepository<StoreCategory, Long> {

    Optional<StoreCategory> findStoreCategoryByName(String name);

    @Transactional
    void deleteById(Long id);
}
