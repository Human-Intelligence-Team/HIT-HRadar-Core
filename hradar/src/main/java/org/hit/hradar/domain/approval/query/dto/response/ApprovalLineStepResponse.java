package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalLineStepResponse {

  private int stepOrder;
  private Long approverId;
  private String status;
  private LocalDateTime actedAt;

}
