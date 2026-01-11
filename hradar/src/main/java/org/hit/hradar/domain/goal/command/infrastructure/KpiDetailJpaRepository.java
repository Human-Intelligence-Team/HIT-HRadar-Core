package org.hit.hradar.domain.goal.command.infrastructure;

import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;
import org.hit.hradar.domain.goal.command.domain.repository.KpiDetailRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiDetailJpaRepository extends KpiDetailRepository,JpaRepository<KpiDetail, Long> {
}
