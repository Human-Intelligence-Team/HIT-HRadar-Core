package org.hit.hradar.domain.approval.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApprovalApproveRequest {

  // 결재 문서 ID
  private Long docId;
}