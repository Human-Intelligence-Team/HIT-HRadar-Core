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
public class ApprovalHistory extends BaseTimeEntity  {

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
  @Column(name = "step_id", nullable = false)
  private Long stepId;

  //결재 타입
  @Enumerated(EnumType.STRING)
  @Column(name = "action_type", nullable = false)
  private ApprovalActionType actionType = ApprovalActionType.SUBMITTED;

  //반려 사유
  @Column(name = "reason", length = 255)
  private String reason;

  //처리 시각
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}
