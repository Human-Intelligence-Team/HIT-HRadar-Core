package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;

@Getter
public class CreateOkrKeyResultRequest {

    private String content;        // KR 내용
    private String metricName;     // 지표명
    private Integer targetValue;   // 목표 수치
}

