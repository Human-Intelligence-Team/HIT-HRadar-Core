package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.query.dto.response.OkrDetailResponseDto;
import org.hit.hradar.domain.goal.query.mapper.OkrDetailMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OkrDetailQueryService {
    private final OkrDetailMapper okrDetailMapper;

    @Transactional(readOnly = true)
    public OkrDetailResponseDto getKrDetail(Long keyResultId) {

        OkrDetailResponseDto summary = okrDetailMapper.findKrSummary(keyResultId);
        if (summary == null) {
            throw new BusinessException(GoalErrorCode.KR_NOT_FOUND);
        }

        summary.setLogs(
                okrDetailMapper.findKrLogs(keyResultId)
        );

        return summary;
    }
}
