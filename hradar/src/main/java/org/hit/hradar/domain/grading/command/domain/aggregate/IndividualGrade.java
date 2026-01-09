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
                        columnNames = {"cycle_id", "emp_id"}
                )
        }
)
@Getter
public class IndividualGrade extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "individual_grade_id")
    private Long individualGradeId;

    //회차 ID
    @Column(name = "cycle_id", nullable = false)
    private Long cycleId;

    // 사원 ID
    @Column(name = "emp_id", nullable = false)
    private Long empId;

    //부여 등급
    @Column(name = "grade_id", nullable = false)
    private Long gradeId;

    //부여 사유
    @Column(name = "grade_reason", nullable = false)
    private String gradeReason;

    //상태
    @Enumerated(EnumType.STRING)
    @Column(name = "grade_status", nullable = false)
    private IndividualGradeStatus gradeStatus = IndividualGradeStatus.DRAFT;

    //created_at, updated_at, created_by, updated_by

    //삭제여부
    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';
}
