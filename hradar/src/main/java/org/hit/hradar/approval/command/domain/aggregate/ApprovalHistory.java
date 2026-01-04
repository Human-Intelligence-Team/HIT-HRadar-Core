package org.hit.hradar.approval.command.domain.aggregate;

import java.time.LocalDateTime;

public class ApprovalHistory {

  //결재 히스토리id
  private Integer historyId;

  //결재 문서id
  private Integer approvalDocumentId;

  //결재자
  private Integer actorId;

  //히스토리 타입
  private String actionType;

  //반려 사유
  private String reason;

  //처리 시각
  private LocalDateTime actedAt;

}
