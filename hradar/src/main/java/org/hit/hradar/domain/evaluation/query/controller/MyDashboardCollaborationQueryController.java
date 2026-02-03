package org.hit.hradar.domain.evaluation.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.query.dto.response.CollaborationRadarResponse;
import org.hit.hradar.domain.evaluation.query.service.CollaborationRadarQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * [개인 대시보드 - 협업 지수(레이더) 조회]
 *
 * - 섹션 제목이 정확히 '협업'인 섹션만 사용
 * - RATING 문항만 사용
 * - 현재 로그인 사용자를 피평가자(evaluatee)로 하여 점수를 집계
 */
@Tag(name = "Evaluation Dashboard - Collaboration", description = "개인 대시보드 - 협업 지수(레이더 차트) 조회 API")
@RestController
@RequestMapping("/my-dashboard/collaboration")
@RequiredArgsConstructor
public class MyDashboardCollaborationQueryController {
    private final CollaborationRadarQueryService service;

    @Operation(summary = "내 협업 지수 조회", description = "현재 로그인한 사용자의 협업 지수 데이터를 레이더 차트 형식으로 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<CollaborationRadarResponse>> getMyCollaborationRadar(
            @CurrentUser AuthUser authUser) {
        // 피평가자 = 현재 로그인 사용자 employeeId
        CollaborationRadarResponse result = service.getMyCollaborationRadar(authUser.employeeId());

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @Operation(summary = "사원별 협업 지수 조회", description = "특정 사원의 협업 지수 데이터를 조회합니다.")
    @GetMapping("/emp")
    public ResponseEntity<ApiResponse<CollaborationRadarResponse>> getCollaborationRadarByEmp(
            @RequestParam Long empId) {
        // 피평가자 = 현재 로그인 사용자 employeeId
        CollaborationRadarResponse result = service.getMyCollaborationRadar(empId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
