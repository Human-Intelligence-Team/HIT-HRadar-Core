package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CycleJpaRepository extends CycleRepository, JpaRepository<Cycle, Long> {
}
