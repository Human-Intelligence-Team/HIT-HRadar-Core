package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateGoalRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalDepth;
import org.hit.hradar.domain.goal.command.domain.aggregate.GoalScope;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GoalCommandService {
    /*
    * Goal (LEVEL_1, KPI) 팀
     ├─ KPI A
     ├─ KPI B
     │
     └─ Goal (LEVEL_2, KPI) 개인 OR 팀
          └─ KPI C
    LEVEL_1 목표는 무조건 팀(TEAM) 목표

    LEVEL_2~3 목표는 개인/팀 선택 가능

    KPI 목표 아래에서만 KPI 생성 가능

    목표는 최대 3단계까지만 생성 가능

    하위 목표는 부모 목표 기간 안에서만 생성 가능*/

    /*팀 KPI 목표 생성 LEVEL_1*/

    private final GoalRepository goalRepository;

    //팀 목표 생성
    public Long createRootGoal(CreateGoalRequest request) {

        //기간 검증
        validatePeriod(request.getStartDate(), request.getEndDate());

        //root 목표는 무조건 팀
        if (request.getGoalScope() != GoalScope.TEAM) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_SCOPE);
        }

        //goalType(KPI or OKR) 반드시 입력
        if (request.getGoalType() == null) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }

        Goal goal = Goal.createRootGoal(
                request.getGoalType(),
                request.getTitle(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate(),
                request.getDepartmentId(),
                request.getOwnerId()
        );

        return goalRepository.save(goal).getGoalId();
    }

    /*하위 목표 생성 LEVEL_2 ~ LEVEL_3*/
    public Long createChildGoal(CreateGoalRequest request) {

        Goal parentGoal = goalRepository.findById(request.getParentGoalId())
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

        //최대 깊이 제한
        if(parentGoal.getDepth() == GoalDepth.LEVEL_3){
            throw new BusinessException(GoalErrorCode.GOAL_DEPTH_EXCEED);
        }

        //기간 검증(부모 범위 내)
        validateChildPeriod(parentGoal, request.getStartDate(), request.getEndDate());

        //TODO: ownerId 로그인 로직후 사용자로부터 가져오기
        Goal childGoal = Goal.createChildGoal(
                parentGoal,
                request.getGoalScope(),
                request.getTitle(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate(),
                request.getOwnerId()
        );

        return goalRepository.save(childGoal).getGoalId();
    }

    //검증
    private void validatePeriod(LocalDate start, LocalDate end) {
        if (end.isBefore(start)) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_PERIOD);
        }
    }

    private void validateChildPeriod(
            Goal parent,
            LocalDate start,
            LocalDate end
    ) {
        validatePeriod(start, end);

        if(start.isBefore(parent.getStartDate())
            || end.isAfter(parent.getEndDate())) {
            throw new BusinessException(
                    GoalErrorCode.INVALID_GOAL_PERIOD
            );
        }
    }
}
