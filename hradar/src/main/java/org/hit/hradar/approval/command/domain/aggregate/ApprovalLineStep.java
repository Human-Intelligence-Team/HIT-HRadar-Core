package org.hit.hradar.approval.command.domain.aggregate;

import java.time.LocalDateTime;

public class ApprovalLineStep {

  //결재 단계id
  private Integer stepId;

  //결재선id
  private Integer approvalLineId;

  //결재 순서
  private Integer stepOrder;

  //결재자 사원id
  private Integer approverId;

  //결재 상태
  private String status;

  //처리 시각
  private LocalDateTime actedAt;

  //처리 이유
  private String reason;

}
