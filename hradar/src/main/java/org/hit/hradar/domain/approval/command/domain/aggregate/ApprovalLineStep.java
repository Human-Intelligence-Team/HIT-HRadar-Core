package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.domain.approval.ApprovalErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;
import org.hit.hradar.global.exception.BusinessException;

@Entity
@Table(name = "approval_line_step")
@Getter
public class ApprovalLineStep extends BaseTimeEntity {

  // 결재 단계 ID (PK)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "step_id")
  private Long stepId;

  // 결재선 ID (FK)
  @Column(name = "line_id", nullable = false)
  private Long lineId;

  // 결재 순서 (1, 2, 3 ...)
  @Column(name = "step_order", nullable = false)
  private int stepOrder;

  // 결재자 사원 ID
  @Column(name = "approver_id", nullable = false)
  private Long approverId;

  // 대리 결재자 사원 ID (optional)
  @Column(name = "proxy_approver_id")
  private Long proxyApproverId;

  // 결재 단계 상태
  // WAITING  : 아직 차례 아님
  // PENDING  : 현재 처리 대상
  // APPROVED : 승인 완료
  // REJECTED : 반려
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ApprovalStepStatus approvalStepStatus = ApprovalStepStatus.WAITING;

  // 실제 처리 시각 (승인/반려 시점)
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

  // 반려 사유
  @Column(name = "reject_reason")
  private String rejectReason;

  // 삭제 여부 (소프트 삭제)
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  // 결재 승인
  public void approve(Long actorId) {
    // 현재 차례가 아니면 승인 불가
    if (this.approvalStepStatus != ApprovalStepStatus.PENDING) {
      throw new BusinessException(
          ApprovalErrorCode.CANNOT_APPROVE_NON_PENDING_STEP
      );
    }

    // 결재자 / 대리결재자만 가능
    if (!isValidApprover(actorId)) {
      throw new BusinessException(
          ApprovalErrorCode.NOT_ALLOWED_APPROVER
      );
    }

    this.approvalStepStatus = ApprovalStepStatus.APPROVED;
    this.actedAt = LocalDateTime.now();
  }

  // 결재 반려
  public void reject(Long actorId, String rejectReason) {
    if (this.approvalStepStatus != ApprovalStepStatus.PENDING) {
      throw new BusinessException(
          ApprovalErrorCode.CANNOT_REJECT_NON_PENDING_STEP
      );
    }

    if (!isValidApprover(actorId)) {
      throw new BusinessException(
          ApprovalErrorCode.NOT_ALLOWED_APPROVER
      );
    }

    if (rejectReason == null || rejectReason.isBlank()) {
      throw new BusinessException(
          ApprovalErrorCode.REJECT_REASON_REQUIRED
      );
    }

    this.approvalStepStatus = ApprovalStepStatus.REJECTED;
    this.rejectReason = rejectReason;
    this.actedAt = LocalDateTime.now();
  }

  // 다음 결재 단계로 활성화
  public void toPending() {
    this.approvalStepStatus = ApprovalStepStatus.PENDING;
  }

  // 결재자 검증 (결재자 or 대리결재자)
  private boolean isValidApprover(Long actorId) {
    return actorId.equals(this.approverId)
        || (this.proxyApproverId != null && actorId.equals(this.proxyApproverId));
  }

  //결재 단계 생성
  public static ApprovalLineStep create(
      Long lineId,
      int order,
      Long approverId,
      boolean isFirst
  ) {
    ApprovalLineStep step = new ApprovalLineStep();
    step.lineId = lineId;
    step.stepOrder = order;
    step.approverId = approverId;

    // 첫 단계만 PENDING, 나머지는 WAITING
    step.approvalStepStatus =
        isFirst ? ApprovalStepStatus.PENDING : ApprovalStepStatus.WAITING;

    return step;
  }
}
