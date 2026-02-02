package org.hit.hradar.domain.approval.query.dto.response;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ApprovalLineStepResponse {

  private int stepOrder;
  private Long approverId;
  private String approverName; // 결재자 이름 추가
  private String status;
  private LocalDateTime actedAt;

}
