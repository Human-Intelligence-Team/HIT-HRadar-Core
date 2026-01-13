package org.hit.hradar.domain.goal.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.goal.query.dto.response.GoalNodeResponseDto;
import org.hit.hradar.domain.goal.query.service.GoalQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
*  - 팀(department)에 속한 모든 Goal 트리를 조회
* - 각 Goal의 "현재 진행률"만 포함
* */
@RestController
@RequiredArgsConstructor
@RequestMapping("/goals")
public class GoalQueryController {

    private final GoalQueryService goalQueryService;

    //팀 Goal 트리 조회
    @GetMapping()
    public ResponseEntity<ApiResponse<List<GoalNodeResponseDto>>> getGoalTree(
            @RequestParam Long departmentId
    ) {

        // Query Service 호출 (모든 계산은 Service에서 끝남)
        List<GoalNodeResponseDto> result =
                goalQueryService.getGoalTree(departmentId);

        // 공통 ApiResponse 래핑
        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
