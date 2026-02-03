package org.hit.hradar.domain.positions.command.domain.repository;

import org.hit.hradar.domain.positions.command.domain.aggregate.Positions;

import java.util.List;
import java.util.Optional;

public interface PositionRepository {

    Positions save(Positions position);

    Optional<Positions> findByPositionIdAndComIdAndIsDeleted(Long positionId, Long comId, char isDeleted);

    boolean existsByNameAndComIdAndIsDeleted(String name, Long comId, char isDeleted);

    Optional<Positions> findByNameAndComIdAndIsDeleted(String name, Long comId, char isDeleted);
}