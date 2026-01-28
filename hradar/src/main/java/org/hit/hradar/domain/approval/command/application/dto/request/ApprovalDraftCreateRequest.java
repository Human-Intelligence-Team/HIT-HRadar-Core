package org.hit.hradar.domain.approval.command.application.dto.request;

import java.util.List;
import lombok.Getter;

//결재 문서 임시저장(DRAFT)요청 DTO
//모든 업무 도메인이 공통 사용
@Getter
public class ApprovalDraftCreateRequest {

  private String docType;
  private String title;
  private String content;
  private List<Long> approverIds;
  private List<Long> referenceIds;
  private Object Payload;
}
