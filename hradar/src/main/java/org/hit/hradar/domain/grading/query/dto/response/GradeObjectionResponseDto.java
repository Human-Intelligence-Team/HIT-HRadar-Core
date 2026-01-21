package org.hit.hradar.domain.grading.query.dto.response;

import lombok.Getter;

@Getter
public class GradeObjectionResponseDto {
    private Long objectionId;
    private String objectionReason;
    private String objectionStatus;
    private String objectionResult;
    private Long resolvedBy;
}
