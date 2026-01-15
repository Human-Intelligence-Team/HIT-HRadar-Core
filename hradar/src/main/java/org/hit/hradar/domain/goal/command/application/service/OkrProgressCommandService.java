package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrProgressRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;
import org.hit.hradar.domain.goal.command.domain.aggregate.OkrProgressLog;
import org.hit.hradar.domain.goal.command.domain.policy.GoalValidationPolicy;
import org.hit.hradar.domain.goal.command.domain.repository.OkrKeyResultRepository;
import org.hit.hradar.domain.goal.command.infrastructure.OkrProgressLogJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OkrProgressCommandService {
    private final OkrKeyResultRepository okrKeyResultRepository;
    private final OkrProgressLogJpaRepository okrProgressLogJpaRepository;

    public void createProgress(
            Long krId,
            Long actorId,
            CreateOkrProgressRequest request
    ){
        //okr 존재 여부 확인
        OkrKeyResult kr = okrKeyResultRepository.findById(krId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.KR_NOT_FOUND));

        //목표
        Goal goal = kr.getGoal();

        //승인된 목표만 가능
        GoalValidationPolicy.validateProgressCreatable(goal);

        //기간 검증
        if (request.getLogDate().isBefore(goal.getStartDate())
                || request.getLogDate().isAfter(goal.getEndDate())) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_PERIOD);
        }

        OkrProgressLog log = OkrProgressLog.builder()
                .keyResult(kr)
                .logDate(request.getLogDate())
                .logOwnerId(actorId)
                .currentProgress(request.getCurrentProgress())
                .build();

        okrProgressLogJpaRepository.save(log);
    }

}
