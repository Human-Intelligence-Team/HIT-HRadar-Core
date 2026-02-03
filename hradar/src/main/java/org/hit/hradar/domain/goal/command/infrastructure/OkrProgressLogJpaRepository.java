package org.hit.hradar.domain.goal.command.infrastructure;

import org.hit.hradar.domain.goal.command.domain.aggregate.OkrProgressLog;
import org.hit.hradar.domain.goal.command.domain.repository.OkrProgressLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OkrProgressLogJpaRepository extends JpaRepository<OkrProgressLog, Long>, OkrProgressLogRepository {
}
