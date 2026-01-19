package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationQuestion;

import java.util.Optional;

public interface EvaluationQuestionRepository {
    EvaluationQuestion save(EvaluationQuestion question);
    Optional<EvaluationQuestion> findById(Long questionId);
    void delete(EvaluationQuestion question);
}
