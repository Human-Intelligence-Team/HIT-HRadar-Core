package org.hit.hradar.domain.grading.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

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
@NoArgsConstructor
public class  IndividualGrade extends BaseTimeEntity {

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
    private GradeApproveStatus gradeStatus = GradeApproveStatus.DRAFT;

    //created_at, updated_at, created_by, updated_by

    //삭제여부
    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';

    @Builder
    public IndividualGrade(
            Long cycleId,
            Long empId,
            Long gradeId,
            String gradeReason
    ) {
        this.cycleId = cycleId;
        this.empId = empId;
        this.gradeId = gradeId;
        this.gradeReason = gradeReason;
    }

    /* ===== 도메인 행위 ===== */

    public void update(Long gradeId, String gradeReason) {
        validateDraft();
        this.gradeId = gradeId;
        this.gradeReason = gradeReason;
    }

    public void submit() {
        validateDraft();
        this.gradeStatus = GradeApproveStatus.SUBMITTED;
    }

    public void approve() {
        if (this.gradeStatus != GradeApproveStatus.SUBMITTED) {
            throw new BusinessException(GradingErrorCode.NOT_ALLOWED);
        }
        this.gradeStatus = GradeApproveStatus.CONFIRMED;
    }

    public void delete() {
        if (this.gradeStatus == GradeApproveStatus.CONFIRMED) {
            throw new BusinessException(GradingErrorCode.NOT_ALLOWED);
        }
        this.isDeleted = 'Y';
    }

    private void validateDraft() {
        if (this.gradeStatus != GradeApproveStatus.DRAFT) {
            throw new BusinessException(GradingErrorCode.NOT_ALLOWED);
        }
    }
}
