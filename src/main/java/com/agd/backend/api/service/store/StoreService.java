package com.agd.backend.api.service.store;

import com.agd.backend.api.entity.Store;
import com.agd.backend.api.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    public Store retrieveStore(UUID id) {
        return storeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public List<Store> ListStore() {
        return storeRepository.findAll();
    }
}
