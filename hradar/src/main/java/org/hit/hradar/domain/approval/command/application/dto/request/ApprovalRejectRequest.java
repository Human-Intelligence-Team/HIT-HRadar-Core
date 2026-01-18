package org.hit.hradar.domain.approval.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApprovalRejectRequest {

  // 결재 문서 ID
  private Long docId;

  // 반려 사유 (필수)
  private String reason;
}
