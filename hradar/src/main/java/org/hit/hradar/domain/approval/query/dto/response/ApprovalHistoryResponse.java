package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalHistoryResponse {

  private String actionType;
  private Long actorId;
  private String reason;
  private LocalDateTime actedAt;

}
