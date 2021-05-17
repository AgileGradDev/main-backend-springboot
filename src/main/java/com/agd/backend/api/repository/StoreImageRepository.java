package com.agd.backend.api.repository;

import com.agd.backend.api.entity.StoreImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface StoreImageRepository extends JpaRepository<StoreImage, UUID> {

    List<StoreImage> findAllByStoreId(UUID storeId);
}
