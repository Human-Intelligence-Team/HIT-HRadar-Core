package org.hit.hradar.domain.positions.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.positions.PositionErrorCode;
import org.hit.hradar.domain.positions.command.application.dto.CreatePositionRequest;
import org.hit.hradar.domain.positions.command.application.dto.UpdatePositionRequest;
import org.hit.hradar.domain.positions.command.domain.aggregate.Positions;
import org.hit.hradar.domain.positions.command.domain.repository.PositionRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;

@Service
@RequiredArgsConstructor
public class PositionCommandService {

  private final PositionRepository positionRepository;
  private final EmployeeRepository employeeRepository;

  private Long requireComId(Long companyId) {
    if (companyId == null) {
      throw new BusinessException(PositionErrorCode.POSITION_NOT_FOUND);
    }
    return companyId;
  }

  @Transactional
  public Long createPosition(CreatePositionRequest request, Long companyId) {
    Long comId = requireComId(companyId);

    // 1. Validate Rank > 0
    if (request.getRank() == null || request.getRank() <= 0) {
      throw new BusinessException(PositionErrorCode.INVALID_POSITION_RANK);
    }

    // 2. Validate Duplicate Name (Absolute Prohibited)
    if (positionRepository.existsByNameAndComIdAndIsDeleted(request.getName(), comId, 'N')) {
      throw new BusinessException(PositionErrorCode.DUPLICATE_POSITION_NAME);
    }

    // 3. Validate Duplicate Rank (Optional extra safety)
    if (positionRepository.existsByRankAndComIdAndIsDeleted(request.getRank(), comId, 'N')) {
      throw new BusinessException(PositionErrorCode.DUPLICATE_POSITION_RANK);
    }

    Positions position = Positions.builder()
        .comId(comId)
        .name(request.getName())
        .rank(request.getRank())
        .build();

    positionRepository.save(position);
    return position.getPositionId();
  }

  @Transactional
  public void updatePosition(Long positionId, Long companyId, UpdatePositionRequest request) {
    Long comId = requireComId(companyId);

    // 1. Validate Rank > 0
    if (request.getRank() == null || request.getRank() <= 0) {
      throw new BusinessException(PositionErrorCode.INVALID_POSITION_RANK);
    }

    Positions position = positionRepository.findByPositionIdAndComIdAndIsDeleted(positionId, comId, 'N')
        .orElseThrow(() -> new BusinessException(PositionErrorCode.POSITION_NOT_FOUND));

    boolean nameChanged = !position.getName().equals(request.getName());
    boolean rankChanged = !position.getRank().equals(request.getRank());

    // 2. Validate Duplicate Name (if changed)
    if (nameChanged && positionRepository.existsByNameAndComIdAndIsDeleted(request.getName(), comId, 'N')) {
      throw new BusinessException(PositionErrorCode.DUPLICATE_POSITION_NAME);
    }

    // 3. Validate Duplicate Rank (if changed)
    if (rankChanged && positionRepository.existsByRankAndComIdAndIsDeleted(request.getRank(), comId, 'N')) {
      throw new BusinessException(PositionErrorCode.DUPLICATE_POSITION_RANK);
    }

    position.updatePosition(request.getName(), request.getRank());
  }

  @Transactional
  public void deletePosition(Long positionId, Long companyId) {
    Long comId = requireComId(companyId);

    Positions position = positionRepository.findByPositionIdAndComIdAndIsDeleted(positionId, comId, 'N')
        .orElseThrow(() -> new BusinessException(PositionErrorCode.POSITION_NOT_FOUND));

    if (employeeRepository.existsByPositionIdAndIsDeleted(positionId, 'N')) {
      throw new BusinessException(PositionErrorCode.CANNOT_DELETE_HAS_EMPLOYEES);
    }

    position.isDeleted();
  }
}
