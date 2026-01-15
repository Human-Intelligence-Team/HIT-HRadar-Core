package org.hit.hradar.domain.goal.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.GoalDetailResponseDto;
import org.hit.hradar.domain.goal.query.service.GoalDetailQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalDetailQueryController {

    private final GoalDetailQueryService goalDetailQueryService;

    /*Goal 기본 정보 상세 조회*/
    @GetMapping("/{goalId}/detail")
    public GoalDetailResponseDto getGoalDetail(@PathVariable("goalId") Long goalId){
        return goalDetailQueryService.getGoalDetail(goalId);
    }
}
