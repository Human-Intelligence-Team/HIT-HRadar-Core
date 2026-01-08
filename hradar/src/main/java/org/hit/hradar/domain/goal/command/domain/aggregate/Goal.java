package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.domain.goal.GoalErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "goal")
@Getter
public class Goal extends BaseTimeEntity {

    // 최대 허용 깊이
    private static final int MAX_DEPTH = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long goalId;

    //상위 목표
    @Column(name = "parent_goal_id")
    private Long parentGoalId;

    // 목표 깊이 (1:상위, 2:중위, 3:하위)
    @Column(name = "goal_depth", nullable = false)
    private int depth;

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
    @Column(name = "goal_dept_id")
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

    //Created_at, Updated_at

    /*@Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;*/

    @Column(name = "is_deleted", nullable = false, length = 1)
    private Character isDeleted = 'N';

    /*@OneToMany(
            mappedBy = "goal",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,  //  Goal 저장 시 KPI 함께 저장
            orphanRemoval = true        //  KPI 제거 시 DB에서도 삭제
    )
    private List<KpiDetail> kpis = new ArrayList<>();*/

}
