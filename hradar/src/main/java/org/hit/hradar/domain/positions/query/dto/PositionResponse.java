package org.hit.hradar.domain.positions.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PositionResponse {
    private Long positionId;
    private Long companyId;
    private String name;
    private Integer rank;
    private char isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
