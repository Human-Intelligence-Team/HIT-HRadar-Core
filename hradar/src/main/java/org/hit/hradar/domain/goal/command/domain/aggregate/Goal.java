package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.domain.goal.command.domain.policy.GoalValidationPolicy;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

import java.time.LocalDate;
import java.util.List;


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
    @Column(name = "goal_title",  length = 200) //임시저장을 위해 null 허용
    private String title;

    //목표 설명
    @Column(name = "goal_description")
    private String description;

    //시작일
    @Column(name = "goal_start_date")//임시저장을 위해 null 허용
    private LocalDate startDate;

    //종료일
    @Column(name = "goal_end_date")//임시저장을 위해 null 허용
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

    //Goal이 자기 KPI/OKR를 알고, 제출 시 규칙을 스스로 검증,통제하기 위해”
    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private List<KpiDetail> kpis = new java.util.ArrayList<>();

    public void addKpi(KpiDetail kpi) {
        if (this.type != GoalType.KPI) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }
        this.kpis.add(kpi);
    }

    @OneToMany(mappedBy = "goal", cascade = CascadeType.ALL)
    private List<OkrKeyResult> okrKeyResults = new java.util.ArrayList<>();

    public void addOkrKeyResult(OkrKeyResult kr) {
        if (this.type != GoalType.OKR) {
            throw new BusinessException(GoalErrorCode.INVALID_PARENT_GOAL_TYPE);
        }
        this.okrKeyResults.add(kr);
    }


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

    //제출 전 수정
    public void updateDraft(
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

        if (scope != null) {
            this.scope = scope;
        }
    }

    //제출 후 수정
    public void updateAfterSubmit(
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

    //제출 메서드
    public void submit() {

        this.approveStatus = GoalApproveStatus.SUBMITTED;
    }

    /* 삭제
     * DRAFT : 일반사용자 0 팀장 0
     * SUBMITTED: 일반사용자 x 팀장 0
     * APPROVED: 일반사용자 x 팀장 0
     * REJECTED: 일반사용자 x 팀장 0*/
    public void delete() {
        this.isDeleted = 'Y';
    }

    //승인
    public void approve() {
        this.approveStatus = GoalApproveStatus.APPROVED;
    }

    //반려
    public void reject(String rejectReason) {
        this.approveStatus = GoalApproveStatus.REJECTED;
        this.rejectReason = rejectReason;
    }

}
