package org.hit.hradar.domain.approval.command.domain.infrastructure;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLineStepJpaRepository
    extends JpaRepository<ApprovalLineStep, Long> {

  /**
   * [병렬 결재]
   * - 특정 결재자가 현재 처리 가능한(PENDING) 결재 단계 조회
   * - 승인/반려 시 actorId 기준으로 사용
   */
  Optional<ApprovalLineStep> findByLineIdAndApproverIdAndApprovalStepStatus(
      Long lineId,
      Long approverId,
      ApprovalStepStatus status
  );

  /**
   * [병렬 결재]
   * - 아직 승인되지 않은 결재 단계가 남아 있는지 확인
   * - 전원 승인 완료 여부 판단용
   */
  boolean existsByLineIdAndApprovalStepStatus(
      Long lineId,
      ApprovalStepStatus status
  );

  /**
   * [회수 판단용]
   * - 승인 또는 반려 이력이 이미 시작되었는지 확인
   */
  boolean existsByLineIdAndApprovalStepStatusIn(
      Long lineId,
      List<ApprovalStepStatus> approvalStepStatuses
  );
}
