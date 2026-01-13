package org.hit.hradar.domain.goal.command.application.dto.request;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateOkrProgressRequest {
    private LocalDate logDate;
    private Integer currentProgress;
}
