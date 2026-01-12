package org.hit.hradar.domain.goal.command.infrastructure;

import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;
import org.hit.hradar.domain.goal.command.domain.repository.OkrKeyResultRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OkrKeyResultJpaRepository extends OkrKeyResultRepository,JpaRepository<OkrKeyResult,Integer> {
}
