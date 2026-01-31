package org.hit.hradar.domain.grading.query.dto.response;

import lombok.Getter;

@Getter
public class TeamGradeDistributionPolicyDto {

    private Long policyId;
    private Long companyId;
    private Long teamGradeId;
    private Long memberGradeId;
    private Integer minRatio;
    private Integer maxRatio;
}