package org.hit.hradar.domain.approval.command.infrastructure;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalLineStep;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStepStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalLineStepJpaRepository
    extends JpaRepository<ApprovalLineStep, Long> {

  //[승인/반려 처리용]
  //특정 결재선(lineId)에서
  //현재 로그인한 결재자(actorId)가
  //처리해야 할 PENDING 단계 조회 → 승인/반려 시 "내 차례인지" 판단의 핵심 쿼리
  Optional<ApprovalLineStep>
  findFirstByLineIdAndApprovalStepStatusAndApproverIdOrderByStepOrderAsc(
      Long lineId,
      ApprovalStepStatus status,
      Long approverId
  );

  //[다음 결재자 활성화용]
  //현재 결재선에서 가장 빠른 WAITING 단계 조회
  //승인 완료 후 다음 결재자를 PENDING으로 변경할 때 사용
  Optional<ApprovalLineStep>
  findFirstByLineIdAndApprovalStepStatusOrderByStepOrderAsc(
      Long lineId,
      ApprovalStepStatus status
  );

  //[전원 승인 여부 판단용] 아직 PENDING 단계가 남아 있는지 확인
  boolean existsByLineIdAndApprovalStepStatus(
      Long lineId,
      ApprovalStepStatus status
  );

  //[회수 가능 여부 판단용]
  //r승인 또는 반려가 이미 시작되었는지 확인
  boolean existsByLineIdAndApprovalStepStatusIn(
      Long lineId,
      List<ApprovalStepStatus> approvalStepStatuses
  );

  // stepOrder 기준으로 가장 첫 번째 결재 단계 조회
  Optional<ApprovalLineStep>
  findFirstByLineIdOrderByStepOrderAsc(Long lineId);

  // [승인/반려 처리용 - 결재자 + 대리결재자 포함]
  Optional<ApprovalLineStep>
  findFirstByLineIdAndApprovalStepStatusAndApproverIdOrProxyApproverIdOrderByStepOrderAsc(
      Long lineId,
      ApprovalStepStatus status,
      Long approverId,
      Long proxyApproverId
  );


}
