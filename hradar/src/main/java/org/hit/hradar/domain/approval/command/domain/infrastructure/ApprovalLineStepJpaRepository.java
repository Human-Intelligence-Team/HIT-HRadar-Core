package org.hit.hradar.domain.approval.command.domain.infrastructure;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ApprovalLineStepJpaRepository
    extends JpaRepository<ApprovalLineStep, Long> {

  // 현재 결재 차례(PENDING) 단계 조회
  Optional<ApprovalLineStep> findByLineIdAndApprovalStepStatus(
      Long lineId,
      ApprovalStepStatus status
  );

  //결재순서 기준으로 특정 단계 조회 (다음 결재 단계 조회)
  Optional<ApprovalLineStep> findByLineIdAndStepOrder(
      Long lineId,
      int stepOrder
  );

  // 승인/반려 이력 존재 여부 (회수 판단용)
  boolean existsByLineIdAndApprovalStepStatusIn(
      Long lineId,
      List<ApprovalStepStatus> approvalStepStatuses
  );

}
