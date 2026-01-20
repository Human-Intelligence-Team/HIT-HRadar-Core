package org.hit.hradar.domain.positions.command.domain.repository;

import org.hit.hradar.domain.positions.command.domain.aggregate.Positions;

import java.util.List;
import java.util.Optional;


public interface PositionRepository {

    Positions save(Positions position);

    Optional<Positions> findByPositionIdAndComIdAndIsDeleted(Long positionId, Long companyId, char isDeleted);

    List<Positions> findAllByComIdAndIsDeleted(Long companyId, char isDeleted);

    boolean existsByNameAndComIdAndIsDeleted(String name, Long companyId, char isDeleted);
}
