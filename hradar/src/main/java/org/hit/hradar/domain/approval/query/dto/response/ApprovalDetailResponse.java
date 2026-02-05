package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class ApprovalDetailResponse {

  // 문서 정보
  private Long docId;
  private String title;
  private String content;
  private String docType;
  private String status;
  private LocalDateTime submittedAt;
  private Long writerId; // 작성자 ID 추가

  // 결재선

  private List<ApprovalLineStepResponse> approvalSteps;
  
  // 참조
  private List<ApprovalReferenceResponse> references;

  // 히스토리
  private List<ApprovalHistoryResponse> histories;

  // 댓글
  private List<ApprovalCommentResponse> comments;

  // Payload (JSON)
  private String payload;

}
