package org.hit.hradar.domain.salary.command.application.dto.request;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;

@Getter
@Setter
public class ApprovalLineStepDTO {

  private Long stepId;  // 결재 단계 ID
  private Long lineId;  // 결재 선 ID
  private Long approverId;  // 결재자 Id
  private int stepOrder;  // 결재 순서
  private Long proxyApproverId; // 대리 결재자 사원Id
  private ApprovalStepStatus approvalStepStatus; // 결재 단계 상태
  private LocalDateTime actedAt; // 실제 처리 시각
  private String rejectReason; // 반려 사유

}
