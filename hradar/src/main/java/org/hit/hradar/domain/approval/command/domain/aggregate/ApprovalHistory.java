package org.hit.hradar.domain.approval.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "APPROVAL_HISTORY")
@Getter
public class ApprovalHistory extends BaseTimeEntity  {

  //결재 이력id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_id")
  private Long historyId;

  //결재 문서id
  @Column(name = "approval_document_id", nullable = false)
  private Long approvalDocumentId;

  //결재자id(행위자)
  @Column(name = "actor_id", nullable = false)
  private Long actorId;

  //결재 단계id
  @Column(name = "step_id", nullable = false)
  private Long stepId;

  //결재 타입
  @Column(name = "action_type", nullable = false, length = 50)
  private String actionType;

  //반려 사유
  @Column(name = "reason", length = 255)
  private String reason;

  //처리 시각
  @Column(name = "acted_at")
  private LocalDateTime actedAt;

  //생성자

  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private String isDeleted;

}
