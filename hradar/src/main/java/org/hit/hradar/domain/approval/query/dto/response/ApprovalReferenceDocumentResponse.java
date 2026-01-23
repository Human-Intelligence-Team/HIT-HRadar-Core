package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalReferenceDocumentResponse {

  // 결재 문서 ID
  private Long docId;

  // 문서 제목
  private String title;

  // 문서 유형
  private String docType;

  // 문서 상태
  private String status;

  // 상신 일시
  private LocalDateTime submittedAt;

}
