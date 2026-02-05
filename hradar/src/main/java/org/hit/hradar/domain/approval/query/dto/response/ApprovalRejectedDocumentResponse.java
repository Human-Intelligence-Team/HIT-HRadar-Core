package org.hit.hradar.domain.approval.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ApprovalRejectedDocumentResponse {

  // 결재 문서 ID
  private Long docId;

  // 문서 제목
  private String title;

  // 문서 유형
  private String docType;

  // 상태
  private String status;

  // 반려 사유
  private String rejectReason;

  // 제출 일시
  private LocalDateTime submittedAt;

  // 반려 일시
  private LocalDateTime rejectedAt;
}
