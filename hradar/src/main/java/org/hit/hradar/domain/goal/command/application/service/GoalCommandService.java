package org.hit.hradar.domain.goal.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.DepartmentErrorCode;
import org.hit.hradar.domain.department.command.domain.aggregate.Department;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateGoalRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.RejectGoalRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.ResubmitGoalRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.UpdateGoalRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.*;
import org.hit.hradar.domain.goal.command.domain.repository.GoalRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.hit.hradar.domain.goal.command.domain.policy.GoalValidationPolicy.*;

@Service
@RequiredArgsConstructor
@Transactional
public class GoalCommandService {
    /*
    * Goal (LEVEL_1, KPI) 팀
     ├─ KPI A
     ├─ KPI B
     └─ Goal (LEVEL_2, KPI) 개인 OR 팀
          └─ KPI C
    LEVEL_1 목표는 무조건 팀(TEAM) 목표
    LEVEL_2~3 목표는 개인/팀 선택 가능
    KPI 목표 아래에서만 KPI 생성 가능
    목표는 최대 3단계까지만 생성 가능
    하위 목표는 부모 목표 기간 안에서만 생성 가능*/

    //모든 등록 == 임시저장 상태
    private final GoalRepository goalRepository;
    private final DepartmentRepository departmentRepository;

    //팀 목표 생성
    public Long createRootGoal(CreateGoalRequest request, Long actorId) {

        //root 목표는 무조건 팀
        if (request.getGoalScope() != GoalScope.TEAM) {
            throw new BusinessException(GoalErrorCode.INVALID_GOAL_SCOPE);
        }

        //goalType(KPI or OKR) 반드시 입력
        if (request.getGoalType() == null) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }

        //기간 검증은 둘 다 있을 때만
        if (request.getStartDate() != null && request.getEndDate() != null) {
            validatePeriod(request.getStartDate(), request.getEndDate());
        }

        Goal goal = Goal.createRootGoal(
                request.getGoalType(),
                request.getTitle(),  // null 가능
                request.getDescription(),  // null 가능
                request.getStartDate(), // null 가능
                request.getEndDate(),  // null 가능
                request.getDepartmentId(),
                actorId
        );

        return goalRepository.save(goal).getGoalId();
    }

    /*하위 목표 생성 LEVEL_2 ~ LEVEL_3*/
    public Long createChildGoal(CreateGoalRequest request, Long actorId) {

        Goal parentGoal = goalRepository.findById(request.getParentGoalId())
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

        //최대 깊이 제한
        validateCreateChildGoal(parentGoal);

        //기간 검증(부모 범위 내)
        if (request.getStartDate() != null && request.getEndDate() != null) {
            validateChildPeriod(parentGoal, request.getStartDate(), request.getEndDate());
        }

        //TODO: ownerId 로그인 로직후 사용자로부터 가져오기
        Goal childGoal = Goal.createChildGoal(
                parentGoal,
                request.getGoalScope(),
                request.getTitle(),
                request.getDescription(),
                request.getStartDate(),
                request.getEndDate(),
                actorId
        );

        return goalRepository.save(childGoal).getGoalId();
    }

    /**
     * Goal 수정
     *
     * 조건:
     * - DRAFT, SUBMITTED 상태만 가능
     * - APPROVED, REJECTED 수정 불가
     * - 개인 목표: 작성자만
     * - 팀 목표: 작성자 or 팀장
     * - 임시저장 수정: 검증은 논리적인 오류 정도만
     * - 제출 후 수정: 모든 검증 수행
     */
    public void updateGoal(Long goalId, UpdateGoalRequest request, Long actorId) {

        //Goal 조회
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));



        //상태 검증 //SUBMITTED 이하
        validateEditable(goal);

        if (goal.getApproveStatus() == GoalApproveStatus.DRAFT) {

            //기간 검증 날짜가 둘 다 들어온 경우에만 검증
            validatePeriod(request.getStartDate(), request.getEndDate());

            // 임시저장 수정
            goal.updateDraft(
                    request.getTitle(),
                    request.getDescription(),
                    request.getStartDate(),
                    request.getEndDate(),
                    request.getScope()
            );

        } else if (goal.getApproveStatus() == GoalApproveStatus.SUBMITTED) {

            // 제출 후 수정
            LocalDate newStart = request.getStartDate();
            LocalDate newEnd = request.getEndDate();

            validatePeriod(newStart, newEnd);

            if (goal.getParentGoalId() != null) {
                Goal parentGoal = goalRepository.findById(goal.getParentGoalId())
                        .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

                validateChildPeriod(parentGoal, newStart, newEnd);
            }

            //모든 필드값이 있는지 검증
            validateRequiredFields(goal);

            goal.updateAfterSubmit(
                    request.getTitle(),
                    request.getDescription(),
                    newStart,
                    newEnd,
                    request.getScope()
            );

        } else {
            // APPROVED / REJECTED
            throw new BusinessException(GoalErrorCode.GOAL_NOT_EDITABLE);
        }
    }

    /**
     * REJECTED Goal 재등록
     *
     * - REJECTED 상태만 가능
     * - 기존 Goal은 수정하지 않음
     * - 새 Goal 생성 (DRAFT 상태)
     * - 자식 Goal인 경우 부모 기간 내 검증 필수
     */
    public Long resubmitGoal(Long goalId, ResubmitGoalRequest request, Long actorId) {

        // 기존(반려된) Goal 조회
        Goal rejectedGoal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));


        // 상태 검증
        validateResubmittable(rejectedGoal);

        // 새 기간 결정 (요청값 없으면 기존 값 유지)
        LocalDate newStart = request.getStartDate() != null
                ? request.getStartDate()
                : rejectedGoal.getStartDate();

        LocalDate newEnd = request.getEndDate() != null
                ? request.getEndDate()
                : rejectedGoal.getEndDate();

        // 자기 자신 기간 검증
        validatePeriod(newStart, newEnd);

        // 자식 Goal인 경우 → 부모 기간 검증
        if (rejectedGoal.getParentGoalId() != null) {

            Goal parentGoal = goalRepository.findById(rejectedGoal.getParentGoalId())
                    .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

            validateChildPeriod(parentGoal, newStart, newEnd);
        }

        // 새 Goal 생성 (복제 + override)
        Goal newGoal = Goal.resubmitFromRejected(
                rejectedGoal,
                request.getTitle(),
                request.getDescription(),
                newStart,
                newEnd,
                request.getScope(),
                actorId
        );

        // 저장 후 새 ID 반환
        return goalRepository.save(newGoal).getGoalId();
    }

    //제출
    public void submitGoal(Long goalId, Long actorId) {

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

        Department department = departmentRepository.findById(goal.getDepartmentId())
                .orElseThrow(() -> new BusinessException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND));

        //삭제되지 않은 goal
        validateNotDeleted(goal);

        // 이미 제출된 경우
        if (goal.getApproveStatus() != GoalApproveStatus.DRAFT) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_SUBMITTABLE);
        }

        //필수값 검증
        validateRequiredFields(goal);

        //KPI/OKR 1개 이상 있는지 검증
        validateGoalIncludeKpiOrOkr(goal);

        // 권한 검증
        validateEditPermission(goal, department,actorId);

        goal.submit();
    }

    //삭제
    public void deleteGoal(Long goalId, Long actorId) {

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));


        //삭제 여부 판단
        validateNotDeleted(goal);

        //TODO: 인사팀의 경우 삭제 권한 추가


        if (goal.getType() == GoalType.KPI) {
            goal.getKpis().forEach(KpiDetail::delete);
        }

        if (goal.getType() == GoalType.OKR) {
            goal.getOkrKeyResults().forEach(OkrKeyResult::delete);
        }
        goal.delete();

    }

    //승인
    public void approveGoal(Long goalId, Long actorId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

        //삭제 여부 판단
        validateNotDeleted(goal);

        // 제출된 건만 반려 가능
        validateSubmitted(goal);

        goal.approve();
    }

    //반려
    public void rejectGoal(Long goalId, RejectGoalRequest request, Long actorId) {
        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new BusinessException(GoalErrorCode.GOAL_NOT_FOUND));

        //삭제 여부 판단
        validateNotDeleted(goal);

        // 제출된 건만 반려 가능
        validateSubmitted(goal);


        //반려 사유 없는 경우 차단
        if(request.getRejectReason() == null || request.getRejectReason().isBlank()) {
            throw new BusinessException(GoalErrorCode.GOAL_REJECT_REASON_REQUIRED);
        }

        goal.reject(request.getRejectReason());
    }


}
