package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrKeyResultRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.hit.hradar.domain.goal.command.domain.repository.OkrKeyResultRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OkrCommandService {

    private final GoalRepository goalRepository;
    private final OkrKeyResultRepository okrKeyResultRepository;

    /**
     * OKR Key Result 생성
     */
    public Long createKeyResult(Long goalId, CreateOkrKeyResultRequest request) {

        //Goal 조회
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

        //OKR 생성 가능 여부 검증
        goal.validateCreatableOkr();

        //KR 생성
        OkrKeyResult kr = OkrKeyResult.create(
                goal,
                request.getContent(),
                request.getMetricName(),
                request.getTargetValue()
        );

        return okrKeyResultRepository.save(kr).getKeyResultId();
    }
}