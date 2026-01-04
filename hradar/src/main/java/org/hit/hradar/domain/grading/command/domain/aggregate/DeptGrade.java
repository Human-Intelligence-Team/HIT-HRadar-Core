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
                        columnNames = {"grade_cycle_id", "dept_id"}
                )
        }
)
@Getter
public class DeptGrade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_grade_id")
    private Long id;

    // 부서
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dept_id", nullable = false)
    private Department department;*/

    // 등급 회차
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_cycle_id", nullable = false)
    private GradeCycle gradeCycle;

    //부여 등급
    @Enumerated(EnumType.STRING)
    @Column(name = "dept_grade", nullable = false)
    private GradeLevel grade;

    //부여 사유
    @Column(name = "grade_reason", nullable = false)
    private String gradeReason;

    // 승인자
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approver_id", nullable = false)
    private Employee approver;*/
}
