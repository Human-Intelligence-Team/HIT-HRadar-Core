package org.hit.hradar.domain.approval.command.application.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalCommentResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalHistoryResponse;
import org.hit.hradar.domain.approval.query.dto.response.ApprovalLineStepResponse;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalDocumentResponse {

  private Long docId;
  private String docType;
  private String title;
  private String content;
  private String status;
  private Long writerId;
  private LocalDateTime submittedAt;

  private ApprovalProgressResponse progress;
  private List<ApprovalLineStepResponse> steps;
  private List<ApprovalHistoryResponse> histories;
  private List<ApprovalCommentResponse> comments;


}
