package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleEvaluationType;

import java.util.List;

public interface CycleEvaluationTypeRepository {

    List<CycleEvaluationType> findByCycleIdAndIsDeleted(Long cycleId, Character isDeleted);

    CycleEvaluationType save(CycleEvaluationType cycleEvaluationType);
}