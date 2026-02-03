package org.hit.hradar.domain.grading.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.grading.GradingErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

@Entity
@Table
@Getter
@NoArgsConstructor
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

    //부여자
    @Column(name = "assigner_id", nullable = false)
    private Long assignerId;

    // 승인자 ID
    @Column(name = "approver_id")
    private Long approverId;

    @Column(name = "grade_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private GradeApproveStatus status =  GradeApproveStatus.DRAFT;

    //삭제여부
    @Column(name = "is_deleted", nullable = false)
    private Character isDeleted = 'N';

    @Builder
    public DeptGrade(
            Long cycleId,
            Long departmentId,
            Long gradeId,
            String gradeReason,
            Long assignerId
    ){
        this.cycleId = cycleId;
        this.departmentId = departmentId;
        this.gradeId = gradeId;
        this.gradeReason = gradeReason;
        this.assignerId = assignerId;
    }

    public void update(Long gradeId, String gradeReason) {
        if (this.status != GradeApproveStatus.DRAFT) {
            throw new BusinessException(GradingErrorCode.NOT_ALLOWED);
        }
        this.gradeId = gradeId;
        this.gradeReason = gradeReason;
    }

    // 제출
    public void submit() {
        if (this.status != GradeApproveStatus.DRAFT) {
            throw new BusinessException(GradingErrorCode.NOT_ALLOWED);
        }
        this.status = GradeApproveStatus.SUBMITTED;
    }

    // 승인
    public void approve(Long approverId) {
        if (this.status != GradeApproveStatus.SUBMITTED) {
            throw new BusinessException(GradingErrorCode.NOT_ALLOWED);
        }
        this.status = GradeApproveStatus.CONFIRMED;
        this.approverId = approverId;
    }

    // 삭제 (DRAFT / SUBMITTED 까지만 허용)
    public void delete() {
        if (this.status == GradeApproveStatus.CONFIRMED) {
            throw new BusinessException(GradingErrorCode.NOT_ALLOWED);        }
        this.isDeleted = 'Y';
    }

}
