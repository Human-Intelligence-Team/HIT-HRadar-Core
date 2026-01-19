package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalActionType;

@Getter
public class ApprovalHistoryResponse {

  private Long historyId;
  private ApprovalActionType actionType;
  private Long actorId;
  private String reason;
  private LocalDateTime actedAt;

}
