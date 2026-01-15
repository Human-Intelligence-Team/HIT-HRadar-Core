package org.hit.hradar.domain.evaluation.query.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CycleListResponseDto {

    private Long cycleId;
    private String cycleName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private Long empId;
}
