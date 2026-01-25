package org.hit.hradar.domain.leave.command.application.listener;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.leave.LeaveErrorCode;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveUsage;
import org.hit.hradar.domain.leave.command.infrastructure.EmpLeaveJpaRepository;
import org.hit.hradar.domain.leave.command.infrastructure.LeaveUsageJpaRepository;
import org.hit.hradar.domain.leave.query.mapper.LeaveUsageMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LeaveApprovalCompletedListener {

  private final LeaveUsageJpaRepository leaveUsageJpaRepository;
  private final EmpLeaveJpaRepository empLeaveJpaRepository;
  private final LeaveUsageMapper leaveUsageMapper;

  //결재 승인 완료 후 자동 실행
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handle(ApprovalEvent event) {

    if (event.getType() != ApprovalEventType.APPROVED) {
      return;
    }

    Long docId = event.getDocId();

// 1. 승인된 휴가 조회
    EmpLeave leave = empLeaveJpaRepository.findByDocId(event.getDocId())
        .orElseThrow(() ->
            new BusinessException(LeaveErrorCode.LEAVE_NOT_FOUND));

    // 2. 여기서 연차 차감 확정
    LeaveUsage usage = LeaveUsage.create(
        leave.getLeaveId(),
        leave.getGrantId(),
        leave.getLeaveDays(),
        leave.getStartDate()
    );

    //연차 차감 확정
    leaveUsageJpaRepository.save(usage);

    // 3. 연차 잔여 일수 차감 (Mapper)
    leaveUsageMapper.decreaseRemainingDays(
        leave.getGrantId(),
        leave.getLeaveDays()
    );
  }
}
