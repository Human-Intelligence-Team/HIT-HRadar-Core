package org.hit.hradar.domain.approval.event;


import org.springframework.stereotype.Component;

@Component
public interface ApprovalEventPublisher {

  void publisher(ApprovalEvent event);

  }

