package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalStepStatus {

  WAITING,    // 승인 대기 (아직 처리 차례 아님 or 대기 중)
  APPROVED,   // 해당 단계 승인 완료
  REJECTED    // 해당 단계 반려

}
