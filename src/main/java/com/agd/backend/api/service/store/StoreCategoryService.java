package com.agd.backend.api.service.store;

import com.agd.backend.api.entity.Store;
import com.agd.backend.api.entity.StoreCategory;
import com.agd.backend.api.repository.StoreCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreCategoryService {

    private final StoreCategoryRepository storeCategoryRepository;

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
}
