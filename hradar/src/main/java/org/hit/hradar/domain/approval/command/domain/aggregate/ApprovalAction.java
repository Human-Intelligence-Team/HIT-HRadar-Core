package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalAction {

  SUBMIT,        // 상신
  APPROVED,      // 승인
  REJECTED,      // 반려
  WITHDRAW,      // 회수
  PROXY_APPROVED // 대리 승인

}
