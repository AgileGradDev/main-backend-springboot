package com.agd.backend.api.service.store;

import com.agd.backend.api.entity.StoreImage;
import com.agd.backend.api.repository.StoreImageRepository;
import com.agd.backend.api.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreImageService {

    private final StoreImageRepository storeImageRepository;
    private final StoreRepository storeRepository;

    public List<StoreImage> listStoreImages(UUID storeId) {
        return storeImageRepository.findAllByStoreId(storeId);
    }

    public StoreImage createStoreImage(UUID storeId, String url) {
        storeRepository.findById(storeId)
                .orElseThrow(IllegalArgumentException::new);

        StoreImage storeImage = new StoreImage(storeId, url);
        return storeImageRepository.save(storeImage);
    }
}
