package com.agd.backend.api.service.store;

import com.agd.backend.api.entity.Store;
import com.agd.backend.api.entity.StoreCategory;
import com.agd.backend.api.entity.StoreStoreCategoryMap;
import com.agd.backend.api.repository.StoreCategoryRepository;
import com.agd.backend.api.repository.StoreStoreCategoryMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCategoryService {

    private final StoreCategoryRepository storeCategoryRepository;
    private final StoreStoreCategoryMapRepository storeStoreCategoryMapRepository;

    public StoreCategory createStoreCategory(String name) {
        Optional<StoreCategory> storeCategory = storeCategoryRepository.findStoreCategoryByName(name);
        if (storeCategory.isPresent()) {
            return storeCategory.get();
        }
        else {
            StoreCategory newStoreCategory = new StoreCategory(name);
            return storeCategoryRepository.save(newStoreCategory);
        }

    }

    public List<StoreCategory> ListStoreCategory(UUID storeId) {
        List<StoreStoreCategoryMap> storeStoreCategoryMaps = storeStoreCategoryMapRepository.findStoreStoreCategoryMapsByStoreId(storeId);
        List<StoreCategory> storeCategories = new ArrayList<StoreCategory>();
        for (StoreStoreCategoryMap map : storeStoreCategoryMaps) {
            Optional<StoreCategory> storeCategory = storeCategoryRepository.findById(
                    map.getStoreCategoryId()
            );
            storeCategory.ifPresent(storeCategories::add);
        }
        return storeCategories;
    }
}
