package org.hit.hradar.domain.grading.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(
        name = "individual_grade",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_cycle_employee",
                        columnNames = {"grade_cycle_id", "emp_id"}
                )
        }
)
@Getter
public class IndividualGrade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "individual_grade_id")
    private Long id;

    // 사원
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;*/

    // 등급 회차
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "grade_cycle_id", nullable = false)
    private GradeCycle gradeCycle;

    //부여 등급
    @Enumerated(EnumType.STRING)
    @Column(name = "individual_grade", nullable = false)
    private GradeLevel grade;

    //부여 사유
    @Column(name = "grade_reason", nullable = false)
    private String gradeReason;

    //상태
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_status", nullable = false)
    private IndividualGradeStatus status = IndividualGradeStatus.DRAFT;

    //created_at, updated_at
}
