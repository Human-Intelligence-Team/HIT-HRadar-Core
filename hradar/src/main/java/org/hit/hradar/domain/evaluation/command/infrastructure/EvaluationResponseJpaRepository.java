package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationResponse;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationResponseRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationResponseJpaRepository extends JpaRepository<EvaluationResponse, Long>, EvaluationResponseRepository {
}
