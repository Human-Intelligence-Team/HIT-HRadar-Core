package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;

import java.util.Optional;

public interface EvaluationTypeRepository {

    boolean existsByCycleIdAndEvalTypeCodeAndIsDeleted(
            Long cycleId,
            String evalTypeCode,
            Character isDeleted
    );

    Optional<EvaluationType> findById(Long evalTypeId);

    EvaluationType save(EvaluationType evaluationType);
}
