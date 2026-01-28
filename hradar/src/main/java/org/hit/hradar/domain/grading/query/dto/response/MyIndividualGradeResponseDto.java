package org.hit.hradar.domain.grading.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyIndividualGradeResponseDto {

    private Long individualGradeId;
    private Long gradeId;
    private String gradeName;
    private String gradeReason;
}
