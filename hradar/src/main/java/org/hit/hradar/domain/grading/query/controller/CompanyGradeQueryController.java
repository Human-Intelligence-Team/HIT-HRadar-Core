package org.hit.hradar.domain.grading.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.security.auth.message.config.AuthConfig;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.CompanyGradeWithRuleListResponseDto;
import org.hit.hradar.domain.grading.query.service.CompanyGradeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Grading - Company Policy", description = "회사 등급 구성 및 배분 정책 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/grades")
public class CompanyGradeQueryController {

    private final CompanyGradeQueryService companyGradeQueryService;

    // 회사 등급 + 배분 정책 조회
    @Operation(summary = "회사 등급 및 정책 조회", description = "회사가 설정한 등급 체계와 각 등급별 점수 배분 가이드라인을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyGradeWithRuleListResponseDto>>> getCompanyGrades(
            @CurrentUser AuthUser authUser) {
        List<CompanyGradeWithRuleListResponseDto> result = companyGradeQueryService
                .getCompanyGradeWithRuleList(authUser.companyId());

        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
