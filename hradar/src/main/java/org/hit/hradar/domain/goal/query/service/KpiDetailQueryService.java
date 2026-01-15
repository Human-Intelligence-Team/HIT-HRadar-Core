package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Param;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.query.dto.response.KpiDetailResponseDto;
import org.hit.hradar.domain.goal.query.mapper.KpiDetailMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class KpiDetailQueryService {

    private final KpiDetailMapper kpiDetailMapper;

    @Transactional(readOnly = true)
    public KpiDetailResponseDto getKpiDetail(Long kpiId) {

        KpiDetailResponseDto response = kpiDetailMapper.findKpiSummary(kpiId);
        if (response == null) {
            throw new BusinessException(GoalErrorCode.KPI_NOT_FOUND);
        }

        response.setLogs(
                kpiDetailMapper.findKpiLogs(kpiId)
        );

        return response;
    }
}
