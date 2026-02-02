package org.hit.hradar.domain.evaluation.query.controller;

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
@RestController
@RequestMapping("/my-dashboard/collaboration")
@RequiredArgsConstructor
public class MyDashboardCollaborationQueryController {
    private final CollaborationRadarQueryService service;

    @GetMapping
    public ResponseEntity<ApiResponse<CollaborationRadarResponse>> getMyCollaborationRadar(
            @CurrentUser AuthUser authUser
    ) {
        // 피평가자 = 현재 로그인 사용자 employeeId
        CollaborationRadarResponse result = service.getMyCollaborationRadar(authUser.employeeId());

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    @GetMapping("/emp")
    public ResponseEntity<ApiResponse<CollaborationRadarResponse>> getCollaborationRadarByEmp(
            @RequestParam Long empId
    ) {
        // 피평가자 = 현재 로그인 사용자 employeeId
        CollaborationRadarResponse result = service.getMyCollaborationRadar(empId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
