package org.hit.hradar.domain.leave.command.application.listener;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.leave.command.domain.repository.EmpLeaveRepository;
import org.hit.hradar.domain.leave.command.domain.repository.LeaveUsageRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LeaveApprovalCompletedListener {

  private final LeaveUsageRepository leaveUsageRepository;

  //결재 승인 완료 후 자동 실행
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void Handle(ApprovalEvent event) {

    if (event.getType() != ApprovalEventType.APPROVED) {
      return;
    }

    Long docId = event.getDocId();

    //연차 차감 확정
    leaveUsageRepository.applyUsage(docId);



  }



}
