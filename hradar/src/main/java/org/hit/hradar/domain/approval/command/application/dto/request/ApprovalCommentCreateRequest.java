package org.hit.hradar.domain.approval.command.application.dto.request;

import lombok.Getter;

@Getter
public class ApprovalCommentCreateRequest {
  private String content;
  private Long parentCommentId;
}