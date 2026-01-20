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
@Table(name = "approval_history")
@Getter
public class ApprovalHistory extends BaseTimeEntity {

  //결재 이력id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_id")
  private Long historyId;

  //결재 문서id
  @Column(name = "doc_id", nullable = false)
  private Long docId;

  //결재자id(행위자)
  @Column(name = "actor_id", nullable = false)
  private Long actorId;

  //결재 단계id
  @Column(name = "step_id")
  private Long stepId;

  //결재 타입
  @Enumerated(EnumType.STRING)
  @Column(name = "action_type", nullable = false)
  private ApprovalActionType approvalActionType = ApprovalActionType.SUBMITTED;

  //반려 사유
  @Column(name = "reason", length = 255)
  private String reason;

  //처리 시각
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  //JPA 기본 생성자
  protected ApprovalHistory() {
  }

  //내부 생성자
  private ApprovalHistory(
      Long docId,
      Long actorId,
      Long stepId,
      ApprovalActionType actionType,
      String reason
  ) {
    this.docId = docId;
    this.actorId = actorId;
    this.stepId = stepId;
    this.approvalActionType = actionType;
    this.reason = reason;
    this.actedAt = LocalDateTime.now();
  }

  // 승인 이력 생성
  public static ApprovalHistory approved(
      Long docId,
      Long actorId,
      ApprovalLineStep step
  ) {
    return new ApprovalHistory(
        docId,
        actorId,
        step.getStepId(),
        ApprovalActionType.APPROVED,
        null
    );
  }

  // 반려 이력 생성
  public static ApprovalHistory rejected(
      Long docId,
      Long actorId,
      ApprovalLineStep step,
      String reason
  ) {
    return new ApprovalHistory(
        docId,
        actorId,
        step.getStepId(),
        ApprovalActionType.REJECTED,
        reason
    );
  }

  public static ApprovalHistory withdraw(
      Long docId,
      Long actorId
  ) {
    return new ApprovalHistory(
        docId,
        actorId,
        null,
        ApprovalActionType.WITHDRAW,
        null
    );
  }

  public static ApprovalHistory submit(
      Long docId,
      Long actorId
  ) {
      return new ApprovalHistory(
          docId,
          actorId,
          null,
          ApprovalActionType.SUBMITTED,
          null
      );
    }
  }