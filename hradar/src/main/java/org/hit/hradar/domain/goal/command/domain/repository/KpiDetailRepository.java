package org.hit.hradar.domain.goal.command.domain.repository;

import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;

import java.util.Optional;

public interface KpiDetailRepository {
    Optional<KpiDetail> findById(Long id);
    KpiDetail save(KpiDetail kpi);
}
