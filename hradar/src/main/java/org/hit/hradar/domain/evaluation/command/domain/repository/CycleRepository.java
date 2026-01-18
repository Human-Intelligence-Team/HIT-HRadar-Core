package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.CycleStatus;

import java.util.List;
import java.util.Optional;

public interface CycleRepository {
    Cycle save(Cycle cycle);
    Optional<Cycle> findById(Long cycleId);

    //자동 상태 전환용
    List<Cycle> findAllByStatusIn(List<CycleStatus> statuses);

}
