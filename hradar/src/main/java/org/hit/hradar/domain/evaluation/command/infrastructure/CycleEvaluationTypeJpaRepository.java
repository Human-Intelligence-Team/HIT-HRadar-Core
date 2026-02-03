package org.hit.hradar.domain.evaluation.command.infrastructure;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleEvaluationTypeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CycleEvaluationTypeJpaRepository
        extends JpaRepository<CycleEvaluationType, Long>, CycleEvaluationTypeRepository {

    @Override
    List<CycleEvaluationType> findByCycleIdAndIsDeleted(Long cycleId, Character isDeleted);
}