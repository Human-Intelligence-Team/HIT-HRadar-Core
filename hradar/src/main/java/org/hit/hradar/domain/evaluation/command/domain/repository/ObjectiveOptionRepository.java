package org.hit.hradar.domain.evaluation.command.domain.repository;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.ObjectiveOption;

import java.util.Optional;

public interface ObjectiveOptionRepository {
    Optional<ObjectiveOption> findById(Long optionId);

}
