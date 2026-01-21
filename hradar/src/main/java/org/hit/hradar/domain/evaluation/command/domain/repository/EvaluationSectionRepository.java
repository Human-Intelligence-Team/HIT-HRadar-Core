package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationSection;

import java.util.Optional;

public interface EvaluationSectionRepository {

    EvaluationSection save(EvaluationSection evaluationSection);

    Optional<EvaluationSection> findById(Long evalTypeId);
}
