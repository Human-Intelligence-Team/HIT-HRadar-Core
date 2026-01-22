package org.hit.hradar.domain.approval.command.domain.aggregate;

public enum ApprovalStatus {

  DRAFT,        // 작성중
  SUBMITTED,    // 제출
  IN_PROGRESS,  // 결재 진행중
  APPROVED,     // 결재 승인
  REJECTED,     // 반려
  WITHDRAWN     // 회수
}
