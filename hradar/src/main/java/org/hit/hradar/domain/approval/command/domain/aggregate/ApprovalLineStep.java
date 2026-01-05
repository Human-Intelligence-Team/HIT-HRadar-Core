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

@Entity
@Table(name = "APPROVAL_LINE_STEP")
@Getter
public class ApprovalLineStep {

  //결재 단계id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "step_id")
  private Long stepId;

  //결재선id
  @Column(name = "approval_line_id", nullable = false)
  private Long approvalLineId;

  //결재 순서
  @Column(name = "step_order", nullable = false)
  private Long stepOrder;

  //결재자 사원id(퇴사한 사원도 보관)
  @Column(name = "approver_id", nullable = false)
  private Long approverId;

  //결재 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ApprovalStepStatus status;

  //처리 시각
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

  //처리 이유
  @Column(name = "reason")
  private String reason;

}
