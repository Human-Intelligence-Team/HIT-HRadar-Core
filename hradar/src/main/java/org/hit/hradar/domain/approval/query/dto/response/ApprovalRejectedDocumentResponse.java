package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;

public class ApprovalRejectedDocumentResponse {

  // 결재 문서 ID
  private Long docId;

  // 문서 제목
  private String title;

  // 반려 사유
  private String rejectReason;

  // 반려 일시
  private LocalDateTime rejectedAt;
}
