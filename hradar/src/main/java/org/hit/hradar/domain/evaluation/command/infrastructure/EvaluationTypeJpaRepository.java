package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EvaluationTypeJpaRepository
extends JpaRepository<EvaluationType, Long>, EvaluationTypeRepository {

    @Override
    boolean existsByCycleIdAndEvalTypeCodeAndIsDeleted(
            Long cycleId,
            String evalTypeCode,
            Character isDeleted
    );


}
