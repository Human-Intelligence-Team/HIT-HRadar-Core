package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.GoalKpiListResponseDto;
import org.hit.hradar.domain.goal.query.mapper.KpiListMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KpiListQueryService {

    private final KpiListMapper goalKpiListMapper;

    /*Goal에 속한 KPI 목록 조회
    * currentValue = kpi_start_value + SUM(log_value)
      진행률 = (current - start) / (target - start) * 100 */
    @Transactional(readOnly = true)
    public List<GoalKpiListResponseDto> getKpis(Long goalId){
        return goalKpiListMapper.findKpisByGoalId(goalId);
    }
}
