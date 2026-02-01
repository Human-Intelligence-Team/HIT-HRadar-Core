package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalActionType;

@Getter
public class ApprovalHistoryResponse {

  private Long historyId;
  private ApprovalActionType actionType;
  private Long actorId;
  private String actorName; // 배우자(결재/기안자 등) 이름
  private String reason;
  private LocalDateTime actedAt;

}
