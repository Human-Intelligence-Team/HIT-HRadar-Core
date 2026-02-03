package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;

import java.util.Optional;

public interface EvaluationTypeRepository {

    boolean existsByCompanyIdAndTypeNameAndIsDeleted(
            Long companyId,
            String typeName,
            Character isDeleted
    );

    EvaluationType save(EvaluationType evaluationType);

    Optional<EvaluationType> findById(Long evalTypeId);
}
