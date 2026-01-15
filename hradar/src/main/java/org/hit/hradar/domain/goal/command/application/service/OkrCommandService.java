package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrKeyResultRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.ResubmitKeyResultRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.UpdateKeyResultRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;
import org.hit.hradar.domain.goal.command.domain.policy.GoalValidationPolicy;
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
        GoalValidationPolicy.validateCreatableOkr(goal);

        //KR 생성
        OkrKeyResult kr = OkrKeyResult.create(
                goal,
                request.getContent(),
                request.getMetricName(),
                request.getTargetValue()
        );

        goal.addOkrKeyResult(kr);

        return okrKeyResultRepository.save(kr).getKeyResultId();
    }

    //수정
    public void updateKeyResult(
            Long goalId,
            Long keyResultId,
            UpdateKeyResultRequest request
    ) {
        OkrKeyResult kr = okrKeyResultRepository.findById(keyResultId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.KR_NOT_FOUND));

        Goal goal = kr.getGoal();

        validateGoalKrRelation(goalId, goal);

        GoalValidationPolicy.validateCreatableOkr(goal);

        kr.update(
                request.getContent(),
                request.getMetricName(),
                request.getTargetValue()
        );
    }

    //재등록
    public Long resubmitKeyResult(
            Long goalId,
            Long keyResultId,
            ResubmitKeyResultRequest request
    ) {
        OkrKeyResult oldKr = okrKeyResultRepository.findById(keyResultId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.KR_NOT_FOUND));

        Goal goal = oldKr.getGoal();

        validateGoalKrRelation(goalId, goal);

        GoalValidationPolicy.validateResubmittable(goal);

        OkrKeyResult newKr = OkrKeyResult.create(
                goal,
                request.getContent() != null ? request.getContent() : oldKr.getContent(),
                request.getMetricName() != null ? request.getMetricName() : oldKr.getOkrMetricName(),
                request.getTargetValue() != null ? request.getTargetValue() : oldKr.getTargetValue()
        );

        return okrKeyResultRepository.save(newKr).getKeyResultId();
    }


    //검증
    //GOAL OKR 연관 검증
    private void validateGoalKrRelation(Long goalId, Goal goal) {
        if (!goal.getGoalId().equals(goalId)) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_OKR_RELATION);
        }
    }
}