package org.hit.hradar.domain.grading.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.MyIndividualGradeResponseDto;
import org.hit.hradar.domain.grading.query.service.IndividualGradeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Grading - Individual Grade", description = "사원 본인 등급 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("individual-grades")
public class IndividualGradeQueryController {

        private final IndividualGradeQueryService individualGradeQueryService;

        @Operation(summary = "내 등급 조회", description = "현재 로그인한 사원에게 부여된 최종 등급 정보를 조회합니다.")
        @GetMapping("/my")
        public ResponseEntity<ApiResponse<MyIndividualGradeResponseDto>> getMyGrade(
                        @CurrentUser AuthUser authUser,
                        @RequestParam Long cycleId) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                individualGradeQueryService.getMyIndividualGrade(
                                                                authUser.employeeId(),
                                                                cycleId)));
        }
}
