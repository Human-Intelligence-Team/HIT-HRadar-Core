package org.hit.hradar.approval.command.domain.aggregate;

import java.time.LocalDateTime;

public class ApprovalReference {

  //참조id
  private Integer referenceId;

  //결재 문서id
  private Integer approvalDocumentId;

  //참조 사원id
  private Integer refEmpId;

  //참조인 지정
  private LocalDateTime createdAt;
}
