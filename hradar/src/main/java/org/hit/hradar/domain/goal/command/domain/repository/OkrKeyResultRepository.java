package org.hit.hradar.domain.goal.command.domain.repository;

import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;

public interface OkrKeyResultRepository {
    OkrKeyResult save(OkrKeyResult kr);
}
