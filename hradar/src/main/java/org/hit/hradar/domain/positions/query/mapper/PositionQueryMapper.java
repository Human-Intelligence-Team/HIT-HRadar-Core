package org.hit.hradar.domain.positions.query.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.hit.hradar.domain.positions.query.dto.PositionResponse;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PositionQueryMapper {

    Optional<PositionResponse> findPositionById(Long positionId, Long comId);

    List<PositionResponse> findAllPositionsByCompany(Long comId);
}
