package org.hit.hradar.domain.grading.command.aplication.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateTeamGradeDistributionPolicyRequestDto {

    private Integer minRatio;
    private Integer maxRatio;
}