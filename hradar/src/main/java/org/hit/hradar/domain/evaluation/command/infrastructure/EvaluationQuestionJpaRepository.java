package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationQuestion;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationQuestionRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationQuestionJpaRepository extends JpaRepository<EvaluationQuestion,Long>, EvaluationQuestionRepository {
}
