package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

import java.time.LocalDate;


@Entity
@Table(name = "goal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Goal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    //상위 목표
    @Column(name = "parent_goal_id")
    private Long parentGoalId;

    // 목표 깊이 (1:상위, 2:중위, 3:하위)
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_depth", nullable = false)
    private GoalDepth depth;

    //개인 or 팀
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_scope", nullable = false)
    private GoalScope scope; //PERSONAL, TEAM

    //kpi or okr
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", nullable = false)
    private GoalType type; //KPI, OKR

    //목표명
    @Column(name = "goal_title", nullable = false, length = 200)
    private String title;

    //목표 설명
    @Column(name = "goal_description")
    private String description;

    //시작일
    @Column(name = "goal_start_date", nullable = false)
    private LocalDate startDate;

    //종료일
    @Column(name = "goal_end_date", nullable = false)
    private LocalDate endDate;

    //부서 ID
    @Column(name = "goal_dept_id", nullable = false)
    private Long departmentId;

    //작성자
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    //진행 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_status", nullable = false)
    private GoalProgressStatus progressStatus = GoalProgressStatus.WAIT;

    //승인 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_approve_status", nullable = false)
    private GoalApproveStatus approveStatus = GoalApproveStatus.DRAFT;

    //반려 사유
    @Column(name = "reject_reason", length = 500)
    private String rejectReason;

    //Created_at, Updated_at, Created_by, Updated_by

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    //=======================================================

    //기본 Goal만들기
    @Builder
    private Goal(
            Long parentGoalId,
            GoalDepth depth,
            GoalScope scope,
            GoalType type,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            Long departmentId,
            Long ownerId
    ) {
        this.parentGoalId = parentGoalId;
        this.depth = depth;
        this.scope = scope;
        this.type = type;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.departmentId = departmentId;
        this.ownerId = ownerId;

        // 생성 시 기본 상태 세팅
        this.progressStatus = GoalProgressStatus.WAIT;
        this.approveStatus = GoalApproveStatus.DRAFT;
        this.isDeleted = 'N';
    }



    /**
     * LEVEL_1 목표 생성
     *
     * 무조건 TEAM 목표
     * KPI 또는 OKR 가능
     * 부모 목표 없음
     */
    public static Goal createRootGoal(
            GoalType type,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            Long departmentId,
            Long ownerId
    ){
        return Goal.builder()
                .depth(GoalDepth.LEVEL_1)
                .scope(GoalScope.TEAM)
                .type(type)
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .departmentId(departmentId)
                .ownerId(ownerId)
                .build();
    }

    /**
     * LEVEL_2 ~ LEVEL_3 하위 목표 생성
     *
     * 부모 목표 필요
     * 부모와 동일한 KPI/OKR 타입
     * 최대 LEVEL_3 까지만 허용
     * 기간은 부모 목표 기간 내
     */
    public static Goal createChildGoal(
            Goal parent,
            GoalScope scope, // PERSONAL or TEAM
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            Long ownerId
    ){

        return Goal.builder()
                .parentGoalId(parent.getGoalId())
                .depth(GoalDepth.nextDepth(parent.getDepth()))
                .scope(scope)
                .type(parent.getType()) // 부모와 KPI/OKR 타입 동일
                .title(title)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .departmentId(parent.getDepartmentId())
                .ownerId(ownerId)
                .build();
    }

    //수정 기능 메서드
    public void updateAll(
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            GoalScope scope
    ) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.scope = scope;
    }

    //반려시 재등록 메서드
    public static Goal resubmitFromRejected(
            Goal rejected,
            String title,
            String description,
            LocalDate startDate,
            LocalDate endDate,
            GoalScope scope,
            Long ownerId
    ) {
        rejected.validateResubmittable();

        return Goal.builder()
                .parentGoalId(rejected.getParentGoalId())
                .depth(rejected.getDepth())
                .type(rejected.getType())
                .scope(scope != null ? scope : rejected.getScope())
                .title(title != null ? title : rejected.getTitle())
                .description(description != null ? description : rejected.getDescription())
                .startDate(startDate != null ? startDate : rejected.getStartDate())
                .endDate(endDate != null ? endDate : rejected.getEndDate())
                .departmentId(rejected.getDepartmentId())
                .ownerId(ownerId)
                .build(); // approveStatus = DRAFT
    }

    //========================검증=============================
    public void validateCreatableKpi() {

        // 삭제된 목표
        if (this.isDeleted == 'Y') {
            throw new BusinessException(GoalErrorCode.GOAL_ALREADY_DELETED);
        }

        // KPI 타입 Goal에서만 KPI 생성 가능
        if (this.type != GoalType.KPI) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }

        //TODO: KPI 생성 가능 개수 제한
    }

    public void validateCreatableOkr() {

        // 삭제된 목표
        if (this.isDeleted == 'Y') {
            throw new BusinessException(GoalErrorCode.GOAL_ALREADY_DELETED);
        }

        // OKR 타입 Goal에서만 KR 생성 가능
        if (this.type != GoalType.OKR) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }

        // TODO: KR 최대 개수 제한
    }

    //수정 가능 상태 검증
    public void validateEditable() {
        //APPROVED / REJECTED는 수정 불가
        if (this.approveStatus == GoalApproveStatus.APPROVED
                || this.approveStatus == GoalApproveStatus.REJECTED) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_EDITABLE);
        }
    }
    public void validateEditableForKpiOkr() {
        validateEditable();
    }

    //REJECTED 반려시에만 재등록 가능
    public void validateResubmittable() {
        if (this.approveStatus != GoalApproveStatus.REJECTED) {
            throw new BusinessException(GoalErrorCode.GOAL_NOT_RESUBMITTABLE);
        }
        if (this.isDeleted == 'Y') {
            throw new BusinessException(GoalErrorCode.GOAL_ALREADY_DELETED);
        }
    }

}
