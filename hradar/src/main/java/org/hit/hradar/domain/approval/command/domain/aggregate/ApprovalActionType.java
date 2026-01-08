package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalActionType {

  SUBMIT,     // 기안자가 문서 제출
  APPROVE,    // 결재자가 승인
  REJECT,     // 결재자가 반려
  WITHDRAW   // 기안자가 회수

}
