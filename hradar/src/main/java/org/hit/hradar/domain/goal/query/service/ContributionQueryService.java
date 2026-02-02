package org.hit.hradar.domain.goal.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.ContributionBarChartResponse;
import org.hit.hradar.domain.goal.query.dto.response.row.ContributionByTeamGoalRow;
import org.hit.hradar.domain.goal.query.mapper.ContributionMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContributionQueryService {

    private final ContributionMapper mapper;

    public ContributionBarChartResponse getMuContributionBarChart(Long empId) {
        List<ContributionByTeamGoalRow> rows
                = mapper.selectMyContributionByTeamGoals(empId);

        List<String> categories = rows.stream()
                .map(ContributionByTeamGoalRow::getTeamGoalTitle)
                .collect(Collectors.toList());

        List<Double> values = rows.stream()
                .map(r -> r.getMyContributionPercent() == null ? 0.0 : r.getMyContributionPercent())
                .collect(Collectors.toList());

        return new ContributionBarChartResponse(categories, values);
    }
}
