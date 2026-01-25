package org.hit.hradar.domain.approval.event;

import lombok.Getter;

@Getter
public class ApprovalEvent {

  private final ApprovalEventType type;
  private final Long docId;
  private final Long compnayId;
  private final Long actorId;

  public ApprovalEvent(
      ApprovalEventType type,
      Long docId,
      Long compnayId,
      Long actorId
  ) {
    this.type = type;
    this.docId = docId;
    this.compnayId = compnayId;
    this.actorId = actorId;
  }

}
