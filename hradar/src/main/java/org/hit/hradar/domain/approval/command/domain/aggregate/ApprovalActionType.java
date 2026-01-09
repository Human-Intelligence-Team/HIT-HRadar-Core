package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalActionType {

  SUBMITTED,     // 제출된 문서
  APPROVED,    // 결재자가 승인
  REJECTED,     // 결재자가 반려
  WITHDRAW   // 기안자가 회수

}
