package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateKpiProgressRequest {
    private LocalDate logDate;
    private Integer logValue;
}
