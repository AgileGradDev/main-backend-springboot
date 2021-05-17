package com.agd.backend.api.service.store;

import com.agd.backend.api.entity.StoreCategory;
import com.agd.backend.api.entity.StoreStoreCategoryMap;
import com.agd.backend.api.repository.StoreCategoryRepository;
import com.agd.backend.api.repository.StoreStoreCategoryMapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreStoreCategoryMapService {

    private final StoreStoreCategoryMapRepository storeStoreCategoryMapRepository;
    private final StoreCategoryRepository storeCategoryRepository;

    public StoreStoreCategoryMap createStoreStoreCategory(UUID storeId, String storeCategoryName) {
        Long storeCategoryId;
        Optional<StoreCategory> storeCategory = storeCategoryRepository.findStoreCategoryByName(storeCategoryName);

        if (!storeCategory.isPresent()) {
            StoreCategory newStoreCategory = storeCategoryRepository.save(new StoreCategory(storeCategoryName));
            storeCategoryId = newStoreCategory.getId();
        }
        else {
            storeCategoryId = storeCategory.get().getId();
        }

        Optional<StoreStoreCategoryMap> storeStoreCategoryMap = storeStoreCategoryMapRepository.
                findStoreStoreCategoryMapByStoreIdAndStoreCategoryId(storeId, storeCategoryId);
        if (storeStoreCategoryMap.isPresent()) {
            return storeStoreCategoryMap.get();
        }
        else {
            StoreStoreCategoryMap newStoreStoreCategoryMap = new StoreStoreCategoryMap(storeId, storeCategoryId);
            return storeStoreCategoryMapRepository.save(newStoreStoreCategoryMap);
        }
    }

}
