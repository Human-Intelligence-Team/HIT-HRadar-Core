package org.hit.hradar.domain.goal.command.domain.policy;

import org.hit.hradar.domain.department.command.domain.aggregate.Department;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.domain.aggregate.*;
import org.hit.hradar.global.exception.BusinessException;

import java.time.LocalDate;

public class GoalValidationPolicy {


    /*공통*/
    public static void validateNotDeleted(Goal goal) {
        if(goal.getIsDeleted() == 'Y') {
            throw new BusinessException(GoalErrorCode.GOAL_ALREADY_DELETED);
        }
    }

    public static void validateEditable(Goal goal) {
        if (goal.getApproveStatus() == GoalApproveStatus.APPROVED
                || goal.getApproveStatus() == GoalApproveStatus.REJECTED) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_EDITABLE);
        }
    }

    //기간 검증
    public static void validatePeriod(LocalDate start, LocalDate end) {
        if (end.isBefore(start) || start.isAfter(end)) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_PERIOD);
        }
    }

    //생성
    public static void validateCreateChildGoal(Goal parent) {
        validateNotDeleted(parent);

        if (parent.getDepth() == GoalDepth.LEVEL_3) {
            throw new BusinessException(GoalErrorCode.GOAL_DEPTH_EXCEED);
        }
    }

    //모든 필드값 들어있는지
    //제출 검증(모든 필드 값이 채워져 있는지 여부)
    public static void validateRequiredFields(Goal goal) {

        if (goal.getTitle() == null || goal.getTitle().isBlank()) {
            throw new BusinessException(GoalErrorCode.GOAL_TITLE_REQUIRED);
        }

        if (goal.getStartDate() == null || goal.getEndDate() == null) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_PERIOD);
        }

        if (goal.getScope() == null || goal.getType() == null) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_TYPE);
        }

        if (goal.getDepth() == GoalDepth.LEVEL_1
                && goal.getScope() != GoalScope.TEAM) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_SCOPE);
        }
    }

    //KPI/OKR 1개 이상 있는지 검증
    public static void validateGoalIncludeKpiOrOkr(Goal goal) {
        if (goal.getType() == GoalType.KPI) {

            if (goal.getKpis().isEmpty()) {
                throw new BusinessException(GoalErrorCode.KPI_REQUIRED);
            }


        } else if (goal.getType() == GoalType.OKR) {

            if (goal.getOkrKeyResults().isEmpty()) {
                throw new BusinessException(GoalErrorCode.OKR_REQUIRED);
            }
        }
    }

    //kpi 를 만들 수 있는 상태 검증
    public static void validateCreatableKpi(Goal goal) {

        // 삭제된 목표
        GoalValidationPolicy.validateNotDeleted(goal);

        // KPI 타입 Goal에서만 KPI 생성 가능
        if (goal.getType() != GoalType.KPI) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }

        //TODO: KPI 생성 가능 개수 제한
    }

    //okr을 만들 수 있는 상태 검증
    public static void validateCreatableOkr(Goal goal) {

        // 삭제된 목표
        GoalValidationPolicy.validateNotDeleted(goal);

        // OKR 타입 Goal에서만 KR 생성 가능
        if (goal.getType() != GoalType.OKR) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }

        // TODO: KR 최대 개수 제한
    }

    //재등록 할 수 있는지 상태 검증
    public static void validateResubmittable(Goal goal) {
        if (goal.getApproveStatus() != GoalApproveStatus.REJECTED) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_RESUBMITTABLE);
        }
        GoalValidationPolicy.validateNotDeleted(goal);
    }

    //제출 여부 검증
    public static void validateSubmitted(Goal goal) {
        if (goal.getApproveStatus() != GoalApproveStatus.SUBMITTED) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_APPROVABLE);
        }
    }

    //팀장판별
    public static boolean isTeamManager(Department dept, Long actorId){

        return actorId.equals(dept.getManagerEmployeeId());
    }

    public static void validateChildPeriod(
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

    //수정 권한 검증
    public static void validateEditPermission(Goal goal, Department dept, Long actorId) {

        // 작성자면 수정 가능
        if (goal.getOwnerId().equals(actorId)) {
            return;
        }

        //개인 목표 -> 작성자 아니면 수정 불가
        if(goal.getScope() == GoalScope.PERSONAL) {
            throw new BusinessException(GoalErrorCode.GOAL_EDIT_FORBIDDEN);
        }

        // 팀 목표인 경우 -> 팀장인지 확인
        if (goal.getScope() == GoalScope.TEAM
                && !isTeamManager(dept, actorId)) {
            throw new BusinessException(GoalErrorCode.GOAL_EDIT_FORBIDDEN);
        }
    }

    //승인된 목표만 kpi 성과 입력 가능
    public static void validateProgressCreatable(Goal goal) {

        validateNotDeleted(goal);

        if (goal.getApproveStatus() != GoalApproveStatus.APPROVED) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_APPROVED);
        }
    }

}
