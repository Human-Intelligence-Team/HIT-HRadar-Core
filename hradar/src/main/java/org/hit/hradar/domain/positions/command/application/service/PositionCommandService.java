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

@Service
@RequiredArgsConstructor
public class PositionCommandService {

    private final PositionRepository positionRepository;

    @Transactional
    public Long createPosition(CreatePositionRequest request, Long comId) {
        if (positionRepository.existsByNameAndComIdAndIsDeleted(request.getName(), comId, 'N')) {
            throw new BusinessException(PositionErrorCode.DUPLICATE_POSITION_NAME);
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
    public void updatePosition(Long positionId, Long comId, UpdatePositionRequest request) {
        Positions position = positionRepository.findByPositionIdAndComIdAndIsDeleted(positionId, comId, 'N')
                .orElseThrow(() -> new BusinessException(PositionErrorCode.POSITION_NOT_FOUND));

        if (!position.getName().equals(request.getName()) &&
            positionRepository.existsByNameAndComIdAndIsDeleted(request.getName(), comId, 'N')) {
            throw new BusinessException(PositionErrorCode.DUPLICATE_POSITION_NAME);
        }

        position.updatePosition(request.getName(), request.getRank());
    }

    @Transactional
    public void deletePosition(Long positionId, Long comId) {
        Positions position = positionRepository.findByPositionIdAndComIdAndIsDeleted(positionId, comId, 'N')
                .orElseThrow(() -> new BusinessException(PositionErrorCode.POSITION_NOT_FOUND));
        position.isDeleted();
    }
}
