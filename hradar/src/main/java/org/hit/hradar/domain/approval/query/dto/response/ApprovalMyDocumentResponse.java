package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalMyDocumentResponse {
  // 결재 문서 ID
  private Long docId;

  // 문서 제목
  private String title;

  // 문서 유형 (근태정정, 휴가, 연봉 등)
  private String docType;

  // 문서 상태 (DRAFT, IN_PROGRESS, APPROVED, REJECTED)
  private String status;

  // 제출 일시
  private LocalDateTime submittedAt;

}
