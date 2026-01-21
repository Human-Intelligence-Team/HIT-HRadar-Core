package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class CycleDetailResponseDto {

    private Long cycleId;
    private String cycleName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private Long empId;

    private List<String> evaluationTypes;
}
