package org.hit.hradar.domain.goal.command.domain.repository;

import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;

import java.util.Optional;

public interface OkrKeyResultRepository {
    OkrKeyResult save(OkrKeyResult kr);
    Optional<OkrKeyResult> findById(Long id);
}
