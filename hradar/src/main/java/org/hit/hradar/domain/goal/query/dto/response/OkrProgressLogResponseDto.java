package org.hit.hradar.domain.goal.query.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class OkrProgressLogResponseDto {

    private LocalDate logDate;

    private Integer currentProgress;

    private Integer progressRate;
}
