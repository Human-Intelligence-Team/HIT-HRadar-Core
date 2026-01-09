package org.hit.hradar.domain.goal.command.domain.repository;

import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;

import java.util.Optional;

public interface GoalRepository {
    Goal save(Goal goal);
    Optional<Goal> findById(Long id);
}