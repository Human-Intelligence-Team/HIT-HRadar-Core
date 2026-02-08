package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalCommentResponse {

  private Long commentId;
  private Long parentCommentId; // 부모 댓글 ID 추가
  private Long writerId;
  private String writerName; // 작성자 이름 추가
  private String content;
  private LocalDateTime createdAt;


}
