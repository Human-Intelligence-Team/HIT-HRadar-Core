package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CycleDetailResponseDto {

    private Long cycleId;
    private String cycleName;
    private Quarter quarter;
    private Character is_comp_report_generated;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private Long empId;

    private List<String> evaluationTypes;
}
