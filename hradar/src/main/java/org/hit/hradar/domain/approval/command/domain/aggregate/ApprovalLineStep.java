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
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "approval_line_step")
@Getter
public class ApprovalLineStep extends BaseTimeEntity {

  //결재 단계id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "step_id")
  private Long stepId;

  //결재자 사원id
  @Column(name = "approver_id", nullable = false)
  private Long approverId;

  //대리 결재자 사원id
  @Column(name = "proxy_approver_id")
  private Long proxyApproverId;

  //결재선id
  @Column(name = "line_id", nullable = false)
  private Long lineId;

  //결재 순서
  @Column(name = "step_order", nullable = false)
  private Long stepOrder;

  //결재 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private ApprovalStatus status = ApprovalStatus.DRAFT;

  //처리 시각
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

  //처리 이유
  @Column(name = "reason", length = 255)
  private String reason;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}
