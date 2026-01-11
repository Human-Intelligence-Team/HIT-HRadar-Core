package org.hit.hradar.domain.goal.command.domain.repository;

import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;

public interface KpiDetailRepository {
    KpiDetail save(KpiDetail kpi);
}
