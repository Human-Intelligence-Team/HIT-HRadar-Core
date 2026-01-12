package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.ResubmitKpiRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.UpdateKpiRequest;
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

        goal.addKpi(kpi);

        return kpiDetailRepository.save(kpi).getKpiId();

    }

    //KPI 수정
    public void updateKpi(Long goalId, Long kpiId, UpdateKpiRequest request) {

        KpiDetail kpi = kpiDetailRepository.findById(kpiId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.KPI_NOT_FOUND));

        Goal goal = kpi.getGoal();

        validateGoalKpiRelation(goalId, goal);

        goal.validateEditableForKpiOkr();

        kpi.update(
                request.getMetricName(),
                request.getStartValue(),
                request.getTargetValue()
        );
    }

    //KPI 재등록
    public Long resubmitKpi(Long goalId, Long kpiId, ResubmitKpiRequest request) {

        KpiDetail oldKpi = kpiDetailRepository.findById(kpiId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.KPI_NOT_FOUND));

        Goal goal = oldKpi.getGoal();

        validateGoalKpiRelation(goalId, goal);

        goal.validateResubmittable();

        KpiDetail newKpi = KpiDetail.create(
                goal,
                request.getMetricName() != null ? request.getMetricName() : oldKpi.getKpiMetricName(),
                request.getStartValue() != null ? request.getStartValue() : oldKpi.getKpiStartValue(),
                request.getTargetValue() != null ? request.getTargetValue() : oldKpi.getKpiTargetValue()
        );

        return kpiDetailRepository.save(newKpi).getKpiId();
    }

    //검증
    //Goal KPI 관계 검증
    private void validateGoalKpiRelation(Long goalId, Goal goal) {
        if (!goal.getGoalId().equals(goalId)) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_KPI_RELATION);
        }
    }

}
