package org.hit.hradar.domain.grading.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.CompanyGradeWithRuleListResponseDto;
import org.hit.hradar.domain.grading.query.service.CompanyGradeQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies/{companyId}/grades")
public class CompanyGradeQueryController {

    private final CompanyGradeQueryService companyGradeQueryService;

    //회사 등급 + 배분 정책 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyGradeWithRuleListResponseDto>>> getCompanyGrades(
            @PathVariable Long companyId
    ) {
        List<CompanyGradeWithRuleListResponseDto> result =
                companyGradeQueryService.getCompanyGradeWithRuleList(companyId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

}
