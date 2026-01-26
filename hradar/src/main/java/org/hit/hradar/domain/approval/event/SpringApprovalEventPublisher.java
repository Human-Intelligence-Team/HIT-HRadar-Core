package org.hit.hradar.domain.approval.event;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringApprovalEventPublisher implements ApprovalEventPublisher{

  private final ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publisher(ApprovalEvent event)  {
    applicationEventPublisher.publishEvent(event);
  }
}
