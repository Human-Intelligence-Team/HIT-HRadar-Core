package org.hit.hradar.domain.goal.command.domain.repository;

import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;

import java.util.Optional;

public interface KpiDetailRepository {
    KpiDetail save(KpiDetail kpi);
    Optional<KpiDetail> findById(Long id);
}
