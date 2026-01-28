package org.hit.hradar.domain.attendance.command.application.listener;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.hit.hradar.domain.approval.event.ApprovalEventType;
import org.hit.hradar.domain.attendance.command.infrastructure.AttendanceWorkPlanJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class WorkPlanApprovalListener {

  private final AttendanceWorkPlanJpaRepository attendanceWorkPlanJpaRepository;

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handle(ApprovalEvent event)  {

    if (event.getType() != ApprovalEventType.APPROVED) {
      return;
    }

    attendanceWorkPlanJpaRepository
        .findByDocId(event.getDocId())
        .ifPresent(plan -> {
          plan.approve();
          attendanceWorkPlanJpaRepository.save(plan);
        });

  }





}
