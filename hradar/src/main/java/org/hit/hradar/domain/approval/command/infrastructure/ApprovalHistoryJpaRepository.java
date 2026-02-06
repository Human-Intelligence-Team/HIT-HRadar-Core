package org.hit.hradar.domain.approval.command.infrastructure;

import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalHistoryJpaRepository extends JpaRepository<ApprovalHistory, Long> {

  ApprovalHistory save(ApprovalHistory history);

  boolean existsByStepIdAndActorIdAndApprovalActionType(
      Long stepId,
      Long actorId,
      org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalActionType approvalActionType
  );

    

}
