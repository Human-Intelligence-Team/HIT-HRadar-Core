package org.hit.hradar.approval.command.domain.aggregate;

import java.time.LocalDateTime;

public class ApprovalLine {

  //결재선id
  private Integer approvalLineId;

  //생성일시
  private LocalDateTime createdAt;

  //결재문서id
  private Integer approvalDocumentId;

}
