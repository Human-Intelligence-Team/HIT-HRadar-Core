package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.ObjectiveOption;
import org.hit.hradar.domain.evaluation.command.domain.repository.ObjectiveOptionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObjectiveOptionJpaRepository extends JpaRepository<ObjectiveOption,Long> , ObjectiveOptionRepository {
}
