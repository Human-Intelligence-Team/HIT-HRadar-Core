package org.hit.hradar.domain.grading.query.dto.response;

import lombok.Getter;

@Getter
public class CompanyGradeWithRuleListResponseDto {

    private Long gradeId;
    private String gradeName;
    private Integer gradeOrder;

    private Integer minRatio;
    private Integer maxRatio;
}
