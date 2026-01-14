package org.hit.hradar.domain.goal.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.GoalKpiListResponseDto;
import org.hit.hradar.domain.goal.query.service.KpiListQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/goals")
public class KpiListQueryController {

    private final KpiListQueryService goalKpiListQueryService;

    @GetMapping("/{goalId}/kpis")
    public List<GoalKpiListResponseDto> getGoalKpis(
            @PathVariable Long goalId
    ) {
        return goalKpiListQueryService.getKpis(goalId);
    }
}
