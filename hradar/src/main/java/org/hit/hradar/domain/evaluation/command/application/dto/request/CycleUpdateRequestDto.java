package org.hit.hradar.domain.evaluation.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CycleUpdateRequestDto {

    private String year;

    private Quarter quarter;

    private String cycleName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
