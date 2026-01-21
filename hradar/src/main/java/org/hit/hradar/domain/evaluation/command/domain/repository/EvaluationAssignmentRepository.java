package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationAssignment;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;

import java.util.Optional;

public interface EvaluationAssignmentRepository {

    EvaluationAssignment save(EvaluationAssignment assignment);


    Optional<EvaluationAssignment> findById(Long assignmentId);

    boolean existsByEvaluationTypeAndEvaluatorIdAndEvaluateeId(
            EvaluationType evaluationType,
            Long evaluatorId,
            Long evaluateeId
    );
}
