package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalTaskResponse {

  // 결재 문서 ID
  private Long docId;

  // 문서 제목
  private String title;

  // 기안자 이름
  private String drafterName;

  // 문서 유형
  private String docType;

  // 제출 일시
  private LocalDateTime submittedAt;

}
