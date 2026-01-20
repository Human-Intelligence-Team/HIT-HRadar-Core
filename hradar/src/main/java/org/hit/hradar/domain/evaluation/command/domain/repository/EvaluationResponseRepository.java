package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface EvaluationResponseRepository {

    Optional<EvaluationResponse> findByAssignment_AssignmentIdAndQuestion_QuestionId(
            Long assignmentId,
            Long questionId
    );

    EvaluationResponse save(EvaluationResponse evaluationResponse);

    long countByAssignment_AssignmentId(Long assignmentId);

    @Query("""
    select r
    from EvaluationResponse r
    where r.assignment.assignmentId = :assignmentId
      and r.question.questionId = :questionId
""")
    Optional<EvaluationResponse> findByAssignmentIdAndQuestionId(
            @Param("assignmentId") Long assignmentId,
            @Param("questionId") Long questionId
    );

}
