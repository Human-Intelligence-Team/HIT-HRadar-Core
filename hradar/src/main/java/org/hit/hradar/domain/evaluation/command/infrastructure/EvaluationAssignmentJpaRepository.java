package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationAssignmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationAssignmentJpaRepository extends JpaRepository<EvaluationAssignment,Long>, EvaluationAssignmentRepository {
}
