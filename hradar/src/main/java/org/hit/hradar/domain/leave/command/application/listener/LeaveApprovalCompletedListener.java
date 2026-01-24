package org.hit.hradar.domain.leave.command.application.listener;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.leave.LeaveErrorCode;
import org.hit.hradar.domain.leave.command.domain.aggregate.EmpLeave;
import org.hit.hradar.domain.leave.command.domain.repository.EmpLeaveRepository;
import org.hit.hradar.domain.leave.command.domain.repository.LeaveUsageRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LeaveApprovalCompletedListener {

  private final EmpLeaveRepository empLeaveRepository;
  private final LeaveUsageRepository leaveUsageRepository;

  //결재 승인 완료 후 자동 실행
  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void Handle(ApprovalCompletedEvent event) {

    Long docId = event.getDocId();

    //결재 문서(docId)에 연결된 휴가 조회
    EmpLeave leave = empLeaveRepository.findByDocId(docId)
        .orElseThrow(() -> new BusinessException(
            LeaveErrorCode.LEAVE_NOT_FOUND));

    //연차 차감 확정
    leaveUsageRepository.applyUsage(docId);


  }



}
