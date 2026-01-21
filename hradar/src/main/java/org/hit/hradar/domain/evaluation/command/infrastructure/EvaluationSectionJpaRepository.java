package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationSection;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationSectionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationSectionJpaRepository extends JpaRepository<EvaluationSection,Long>, EvaluationSectionRepository {
}
