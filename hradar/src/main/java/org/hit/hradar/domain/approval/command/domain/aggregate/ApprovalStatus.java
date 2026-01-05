package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalStatus {

  DRAFT,      // 임시저장
  SUBMITTED,  // 상신(결재 진행 중)
  APPROVED,   // 최종 승인
  REJECTED,   // 반려
  RECALL      // 회수
}
