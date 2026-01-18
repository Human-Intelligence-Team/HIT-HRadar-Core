package org.hit.hradar.domain.approval.command.domain.repository;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;

public interface ApprovalLineStepRepository {

  // 현재 결재 차례(PENDING) 단계 조회
  Optional<ApprovalLineStep> findByLineIdAndStatus(
      Long lineId,
      ApprovalStepStatus status
  );

  // 다음 결재 단계 조회
  Optional<ApprovalLineStep> findByLineIdAndStepOrder(
      Long lineId,
      int stepOrder
  );

  // 승인/반려 이력 존재 여부 (회수 판단용)
  boolean existsByLineIdAndStatusIn(
      Long lineId,
      List<ApprovalStepStatus> statuses
  );
}
