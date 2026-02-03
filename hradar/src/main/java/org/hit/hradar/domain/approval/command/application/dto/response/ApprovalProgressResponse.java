package org.hit.hradar.domain.approval.command.application.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ApprovalProgressResponse {

  private Integer totalSteps;
  private Integer completedSteps;
  private Integer currentStepOrder;
  private Long currentApproverId;

}
