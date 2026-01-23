package org.hit.hradar.domain.positions.command.infrastructure;

import org.hit.hradar.domain.positions.command.domain.aggregate.Positions;
import org.hit.hradar.domain.positions.command.domain.repository.PositionRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionJpaRepository extends JpaRepository<Positions, Long>, PositionRepository {
}
