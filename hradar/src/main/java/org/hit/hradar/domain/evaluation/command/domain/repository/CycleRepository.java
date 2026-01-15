package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;

import java.util.Optional;

public interface CycleRepository {
    Cycle save(Cycle cycle);
    Optional<Cycle> findById(Long cycleId);
}
