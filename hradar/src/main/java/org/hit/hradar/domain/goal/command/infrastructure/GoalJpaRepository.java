package org.hit.hradar.domain.goal.command.infrastructure;

import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalJpaRepository extends GoalRepository, JpaRepository<Goal, Long>  {
}
