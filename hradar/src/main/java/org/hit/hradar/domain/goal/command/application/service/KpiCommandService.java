package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.hit.hradar.domain.goal.command.domain.repository.KpiDetailRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KpiCommandService {

    private final GoalRepository goalRepository;
    private final KpiDetailRepository kpiDetailRepository;

    /*KPI 생성
    * GOAL 존재여부 확인
    * GOAL_TYPE == KPI
    * */
    public Long createKpi(Long goalId, CreateKpiRequest request) {

        //Goal 조회
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

        //KPI 생성 가능 여부 검증
        goal.validateCreatableKpi();

        //KPI 생성
        KpiDetail kpi = KpiDetail.create(
                goal,
                request.getMetricName(),
                request.getStartValue(),
                request.getTargetValue()
        );

        return kpiDetailRepository.save(kpi).getKpiId();

    }

}
