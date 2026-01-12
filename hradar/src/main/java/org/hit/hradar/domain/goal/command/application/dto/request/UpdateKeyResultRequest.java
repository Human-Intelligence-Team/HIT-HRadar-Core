package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;

@Getter
public class UpdateKeyResultRequest {
    private String content;
    private String metricName;
    private Integer targetValue;
    private Long actorId;
}