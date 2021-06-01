package com.agd.backend.api.repository;

import com.agd.backend.api.entity.StoreStoreCategoryMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface StoreStoreCategoryMapRepository extends JpaRepository<StoreStoreCategoryMap, UUID> {

    public Optional<StoreStoreCategoryMap> findStoreStoreCategoryMapByStoreIdAndStoreCategoryId(UUID storeId, Long storeCategoryId);

    public List<StoreStoreCategoryMap> findStoreStoreCategoryMapsByStoreId(UUID storeId);

}
