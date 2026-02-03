package org.hit.hradar.domain.goal.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.GoalDetailResponseDto;
import org.hit.hradar.domain.goal.query.service.GoalDetailQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Goal Detail Query", description = "목표 상세 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalDetailQueryController {

    private final GoalDetailQueryService goalDetailQueryService;

    /* Goal 기본 정보 상세 조회 */
    @Operation(summary = "목표 상세 조회", description = "특정 목표(Goal)의 기본 정보 및 상태를 상세 조회합니다.")
    @GetMapping("/{goalId}/detail")
    public GoalDetailResponseDto getGoalDetail(@PathVariable("goalId") Long goalId) {
        return goalDetailQueryService.getGoalDetail(goalId);
    }
}
