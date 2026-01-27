package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateKpiProgressRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.KpiDetail;
import org.hit.hradar.domain.goal.command.domain.aggregate.KpiProgressLog;
import org.hit.hradar.domain.goal.command.domain.repository.KpiDetailRepository;
import org.hit.hradar.domain.goal.command.infrastructure.KpiProgressLogJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.hit.hradar.domain.goal.command.domain.policy.GoalValidationPolicy.validateProgressCreatable;

@Service
@RequiredArgsConstructor
@Transactional
public class KpiProgressCommandService {

    private final KpiDetailRepository kpiDetailRepository;
    private final KpiProgressLogJpaRepository kpiProgressLogJpaRepository;

    public void createProgress(
            Long kpiId,
            Long actorId,
            CreateKpiProgressRequest request
    ){
        //kpi가 존재
        KpiDetail kpi = kpiDetailRepository.findById(kpiId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.KPI_NOT_FOUND));

        Goal goal = kpi.getGoal();

        //성과 입력 가능 상태 검증
        validateProgressCreatable(goal);

        //기간 검증
        if (request.getLogDate().isBefore(goal.getStartDate())
                || request.getLogDate().isAfter(goal.getEndDate())) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_PERIOD);
        }

        KpiProgressLog log = new KpiProgressLog(
                kpi,
                request.getLogDate(),
                actorId,
                request.getLogValue()
        );

        kpiProgressLogJpaRepository.save(log);
    }
}
