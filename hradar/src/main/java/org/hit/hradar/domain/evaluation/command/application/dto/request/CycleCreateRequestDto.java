package org.hit.hradar.domain.evaluation.command.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CycleCreateRequestDto {

    private String year;

    private Quarter quarter;

    private String cycleName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    /*// 마감 N일 전 알림용
    @NotNull
    @PositiveOrZero
    private Integer reminderDays;*/
}
