package org.hit.hradar.domain.goal.query.dto.response.row;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ContributionByTeamGoalRow {
    private Long teamGoalId;
    private String teamGoalTitle;
    private Double myContributionPercent;
    private Double myContributionValue;
    private Double teamTotalContributionValue;
}
