package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalWithdrawHistoryResponse {

  private Long docId;
  private LocalDateTime withdrawnAt;

}
