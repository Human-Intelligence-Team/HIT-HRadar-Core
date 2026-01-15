package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;

@Getter
public class RejectGoalRequest {
    private Long actorId;
    private String rejectReason;
}
