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

  //결재 단계id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "step_id")
  private Long stepId;

  //결재선id
  @Column(name = "line_id", nullable = false)
  private Long lineId;

  //결재 순서(1,2,3..)
  @Column(name = "step_order", nullable = false)
  private int stepOrder;

  //결재자 사원id
  @Column(name = "approver_id", nullable = false)
  private Long approverId;

  //대리 결재자 사원id
  @Column(name = "proxy_approver_id")
  private Long proxyApproverId;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ApprovalStepStatus approvalStepStatus = ApprovalStepStatus.WAITING;

  //처리 시각
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

  // 반려 사유
  @Column(name = "reject_reason")
  private String rejectReason;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  //결재 단계 승인
  public void approve(Long actorId) {
    if (this.approvalStepStatus != ApprovalStepStatus.PENDING) {
      throw new BusinessException(ApprovalErrorCode.CANNOT_SUBMIT_NOT_DRAFT
      );
    }
    if (!isValidApprover(actorId)) {
      throw new BusinessException(
          ApprovalErrorCode.CANNOT_SUBMIT_NOT_DRAFT
      );
    }
    this.approvalStepStatus = ApprovalStepStatus.APPROVED;
    this.actedAt = LocalDateTime.now();
  }

  //결재 단계 반려
  public void reject(Long actorId, String rejectReason) {
    // 현재 결재 차례(PENDING)가 아닌 단계는 반려할 수 없다.
    if (this.approvalStepStatus != ApprovalStepStatus.PENDING){
      throw new BusinessException(
          ApprovalErrorCode.CANNOT_REJECT_NON_PENDING_STEP
      );
    }
    // 결재자 또는 대리결재자가 아닌 사용자는 처리할 수 없다.
    if (!isValidApprover(actorId)) {
      throw new BusinessException(
          ApprovalErrorCode.NOT_ALLOWED_APPROVER
      );
    }
    // 반려 처리 시에는 반드시 반려 사유가 입력되어야 한다.
    if (rejectReason == null || rejectReason.isBlank()) {
      throw new BusinessException(
          ApprovalErrorCode.REJECT_REASON_REQUIRED
      );
    }
    this.approvalStepStatus = ApprovalStepStatus.REJECTED;
    this.rejectReason = rejectReason;
    this.actedAt = LocalDateTime.now();
  }

  //결재 단계를 처리할 수 있는 사원인지 확인한다.(approverId or proxyApproverId)
  private boolean isValidApprover(Long actorId) {
    return actorId.equals(this.approverId)
        || (this.proxyApproverId != null && actorId.equals(this.proxyApproverId));
  }

  // ApprovalLineStep
  public void toPending() {
    this.approvalStepStatus = ApprovalStepStatus.PENDING;
  }



}
