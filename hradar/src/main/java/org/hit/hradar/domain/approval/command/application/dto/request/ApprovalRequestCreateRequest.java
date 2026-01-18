package org.hit.hradar.domain.approval.command.application.dto.request;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApprovalRequestCreateRequest {

  // 문서 제목
  private String title;

  // 문서 본문
  private String content;

  // 문서 유형
  private String documentType;

  // 결재자 사원 ID 목록 (순서 중요)
  private List<Long> approverIds;
}
