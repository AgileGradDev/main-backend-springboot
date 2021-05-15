package com.agd.backend.api.repository;

import com.agd.backend.api.entity.VisitLog;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.UUID;

public interface VisitLogRepository extends JpaRepository<VisitLog, UUID> {

    VisitLog getById(UUID id);

    @Transactional
    void deleteById(UUID id);
}
