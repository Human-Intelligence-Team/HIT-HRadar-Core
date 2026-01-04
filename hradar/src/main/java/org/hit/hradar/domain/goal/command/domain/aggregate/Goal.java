package org.hit.hradar.domain.goal.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDate;

@Entity
@Table(name = "goal")
@Getter
public class Goal extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Long id;

    //상위 목표
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_goal_id")
    private Goal parentGoal;

    //개인 or 팀
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_scope", nullable = false)
    private GoalScope scope;

    //kpi or okr
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type", nullable = false)
    private GoalType type;

    //목표명
    @Column(name = "goal_title", nullable = false, length = 200)
    private String title;

    //목표 설명
    @Column(name = "goal_description")
    private String description;

    /*//부서 ID
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "goal_dept_id")
    private Department department;*/

    /*//작성자
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Employee owner;*/

    //승인 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_approve_status", nullable = false)
    private GoalApproveStatus approveStatus = GoalApproveStatus.DRAFT;

    //반려 사유
    @Column(name = "reject_reason", length = 500)
    private String rejectReason;

    //진행 상태
    @Enumerated(EnumType.STRING)
    @Column(name = "goal_status", nullable = false)
    private GoalProgressStatus progressStatus = GoalProgressStatus.WAIT;

    //시작일
    @Column(name = "goal_start_date", nullable = false)
    private LocalDate startDate;

    //종료일
    @Column(name = "goal_end_date", nullable = false)
    private LocalDate endDate;

    //Created_at, Updated_at
}
