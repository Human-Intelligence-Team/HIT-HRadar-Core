package org.hit.hradar.domain.goal.command.infrastructure;


import org.hit.hradar.domain.goal.command.domain.aggregate.KpiProgressLog;
import org.hit.hradar.domain.goal.command.domain.repository.KpiProgressLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KpiProgressLogJpaRepository extends KpiProgressLogRepository, JpaRepository<KpiProgressLog, Long> {

}
