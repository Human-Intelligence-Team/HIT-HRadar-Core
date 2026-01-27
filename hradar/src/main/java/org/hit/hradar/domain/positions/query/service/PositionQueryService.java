package org.hit.hradar.domain.positions.query.service;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.positions.PositionErrorCode;
import org.hit.hradar.domain.positions.query.dto.PositionResponse;
import org.hit.hradar.domain.positions.query.mapper.PositionQueryMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PositionQueryService {

  private final PositionQueryMapper positionQueryMapper;


  private Long toComId(Long companyId) {
    if (companyId == null) {
      throw new BusinessException(PositionErrorCode.POSITION_NOT_FOUND); // 정책에 맞는 에러로 바꿔도 됨
    }
    return companyId;
  }

  public PositionResponse getPositionById(Long positionId, Long companyId) {
    Long comId = toComId(companyId);

    return positionQueryMapper.findPositionById(positionId, comId)
        .orElseThrow(() -> new BusinessException(PositionErrorCode.POSITION_NOT_FOUND));
  }

  public List<PositionResponse> getAllPositionsByCompany(Long companyId) {
    Long comId = toComId(companyId);

    return positionQueryMapper.findAllPositionsByCompany(comId);
  }
}
