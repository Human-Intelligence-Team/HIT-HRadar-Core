package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;

import java.util.List;
import java.util.Optional;

public interface CycleEvaluationTypeRepository {

    List<CycleEvaluationType> findByCycleIdAndIsDeleted(Long cycleId, Character isDeleted);

    CycleEvaluationType save(CycleEvaluationType cycleEvaluationType);

    Optional<CycleEvaluationType> findById(Long cycleEvalTypeId);
}