package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalCommentResponse {

  private Long commentId;
  private Long writerId;
  private String content;
  private LocalDateTime createdAt;

}
