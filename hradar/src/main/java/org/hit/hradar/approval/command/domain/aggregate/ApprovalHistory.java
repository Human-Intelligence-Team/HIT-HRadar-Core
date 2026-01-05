package org.hit.hradar.approval.command.domain.aggregate;

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
@Table(name = "APPROVAL_HISTORY")
@Getter
public class ApprovalHistory {

  //결재 히스토리id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_id")
  private Long historyId;

  //결재 문서id
  @Column(name = "approval_document_id", nullable = false)
  private Long approvalDocumentId;

  //결재자
  @Column(name = "actor_id", nullable = false)
  private Long actorId;

  //히스토리 타입
  @Enumerated(EnumType.STRING)
  @Column(name = "action_type", nullable = false)
  private ApprovalActionType actionType;

  //반려 사유
  @Column(name = "reason")
  private String reason;

  //처리 시각
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

}
