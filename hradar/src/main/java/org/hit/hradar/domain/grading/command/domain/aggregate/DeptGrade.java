package org.hit.hradar.domain.grading.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(
        name = "dept_grade",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cycle_dept",
                        columnNames = {"cycle_id", "dept_id"}
                )
        }
)
@Getter
public class DeptGrade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_grade_id")
    private Long deptGradeId;

    //평가 회차 ID
    @Column(name = "cycle_id", nullable = false)
    private Long cycleId;


    // 부서 ID
    @Column(name = "dept_id", nullable = false)
    private Long departmentId;


    //부여 등급
    @Column(name = "grade_id", nullable = false)
    private Long gradeId;

    //부여 사유
    @Column(name = "grade_reason", nullable = false)
    private String gradeReason;

    // 승인자 ID
    @Column(name = "approver_id")
    private Long approverId;

    //삭제여부
    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';
}
