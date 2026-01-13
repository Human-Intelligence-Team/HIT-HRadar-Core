package org.hit.hradar.domain.goal.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.command.application.dto.request.CreateGoalRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.RejectGoalRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.ResubmitGoalRequest;
import org.hit.hradar.domain.goal.command.application.dto.request.UpdateGoalRequest;
import org.hit.hradar.domain.goal.command.application.service.GoalCommandService;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalCommandController {

    private final GoalCommandService goalCommandService;

    //GOAL 등록
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

    //GOAL 수정
    @PatchMapping("/{goalId}")
    public ResponseEntity<ApiResponse<String>> updateGoal(
            @PathVariable Long goalId,
            @RequestBody UpdateGoalRequest request
    ){
        goalCommandService.updateGoal(goalId, request);
        return ResponseEntity.ok(ApiResponse.success(goalId.toString()));
    }

    //GOAL 재등록(반려시)
    @PostMapping("/{goalId}/resubmit")
    public ResponseEntity<ApiResponse<String>> resubmitGoal(
            @PathVariable Long goalId,
            @RequestBody ResubmitGoalRequest request
    ) {
        Long newGoalId = goalCommandService.resubmitGoal(goalId, request);
        return ResponseEntity.ok(ApiResponse.success(newGoalId.toString()));
    }

    //제출
    @PostMapping("/{goalId}/submit")
    public ResponseEntity<ApiResponse<String>> submitGoal(
            @PathVariable Long goalId,
            @RequestBody Long actorId
    ) {
        goalCommandService.submitGoal(goalId, actorId);
        return ResponseEntity.ok(ApiResponse.success(goalId.toString()));
    }

    //삭제
    @DeleteMapping("/{goalId}")
    public ResponseEntity<ApiResponse<String>> deleteGoal(
            @PathVariable Long goalId,
            @RequestParam Long actorId
    ) {
        goalCommandService.deleteGoal(goalId, actorId);
        return ResponseEntity.ok(ApiResponse.success(goalId.toString()));
    }

    //승인
    @PostMapping("/{goalId}/approve")
    public ResponseEntity<ApiResponse<String>> approveGoal(
            @PathVariable Long goalId,
            @RequestParam Long actorId
    ) {
        goalCommandService.approveGoal(goalId, actorId);
        return ResponseEntity.ok(ApiResponse.success(goalId.toString()));
    }

    //반려
    @PostMapping("/{goalId}/reject")
    public ResponseEntity<ApiResponse<String>> rejectGoal(
            @PathVariable Long goalId,
            @RequestBody RejectGoalRequest request
    ) {
        goalCommandService.rejectGoal(goalId, request);
        return ResponseEntity.ok(ApiResponse.success(goalId.toString()));
    }
}
