package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CycleUpdateRequestDto {

    //TODO: 연도, 분기

    private String cycleName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
