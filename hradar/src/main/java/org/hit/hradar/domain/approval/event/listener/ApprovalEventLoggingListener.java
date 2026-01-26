package org.hit.hradar.domain.approval.event.listener;


import lombok.extern.slf4j.Slf4j;
import org.hit.hradar.domain.approval.event.ApprovalEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class ApprovalEventLoggingListener {

  @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
  public void handle(ApprovalEvent event) {
    log.info(    "[APPROVAL EVENT] type={}, docId={}, actorId={}",
        event.getType(),
        event.getDocId(),
        event.getActorId()
    );
  }
}
