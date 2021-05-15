package com.agd.backend.api.service.visit_log;

import com.agd.backend.api.entity.Store;
import com.agd.backend.api.entity.VisitLog;
import com.agd.backend.api.repository.StoreJpaRepository;
import com.agd.backend.api.repository.VisitLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class VisitLogService {

    private final VisitLogRepository visitLogRepository;
    private final StoreJpaRepository storeJpaRepository;

    public VisitLog createVisitLog(UUID storeId, float rating, String text) {
        Store store = storeJpaRepository.findById(storeId).orElseThrow(IllegalArgumentException::new);
        VisitLog newVisitLog = new VisitLog(store, rating, text);
        return visitLogRepository.save(newVisitLog);
    }

    public void deleteVisitLog(UUID id) {
        visitLogRepository.deleteById(id);
    }
}
