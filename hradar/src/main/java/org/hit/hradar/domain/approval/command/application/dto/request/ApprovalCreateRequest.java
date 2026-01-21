package org.hit.hradar.domain.approval.command.application.dto.request;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType;

//결재 문서 임시저장(DRAFT)요청 DTO
//모든 업무 도메인이 공통 사용
@Getter
public class ApprovalCreateRequest {

  // 결재 문서 유형 (SALARY, VACATION, ...)
  private ApprovalDocumentType docType;

  // 결재 문서 제목 (프론트 입력)
  private String title;

  // 결재 문서 본문 (프론트 입력)
  private String content;

  // 결재자 사원 ID 목록 (순서 중요)
  private List<Long> approverIds;

  // 참조자 사원 ID 목록 (선택)
  private List<Long> referenceIds;
}
