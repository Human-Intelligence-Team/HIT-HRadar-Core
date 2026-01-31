package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationResponse;

import java.util.Optional;

public interface EvaluationResponseRepository {
    Optional<EvaluationResponse> findByAssignment_AssignmentIdAndQuestion_QuestionId(
            Long assignmentId,
            Long questionId
    );
    EvaluationResponse save(EvaluationResponse response);
}
