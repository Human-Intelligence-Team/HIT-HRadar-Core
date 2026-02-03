package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalStatus {

  DRAFT,        // 임시저장(작성중)
  IN_PROGRESS,  // 결재 진행중 (submit 이후)
  APPROVED,     // 결재 승인
  REJECTED,     // 반려
  WITHDRAWN     // 회수
}
