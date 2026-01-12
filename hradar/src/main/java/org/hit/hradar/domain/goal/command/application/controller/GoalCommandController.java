package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateGoalRequest;
import org.hit.hradar.domain.goal.command.application.service.GoalCommandService;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/goals")
public class GoalCommandController {

    private final GoalCommandService goalCommandService;

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createGoal(
            @RequestBody CreateGoalRequest request
    ) {
        Long goalId;

        //상위목표가 없으면 root, 상위목표가 있으면 child
        if (request.getParentGoalId() == null) {
            goalId = goalCommandService.createRootGoal(request);
        }else {
            goalId = goalCommandService.createChildGoal(request);
        }
        return ResponseEntity.ok(ApiResponse.success(goalId.toString()));
    }
}
