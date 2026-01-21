package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterGradeDistributionRuleRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.GradeDistributionRuleCommandService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies/{companyId}/grades/{gradeId}/distribution-rule")
public class GradeDistributionRuleCommandController {

    private final GradeDistributionRuleCommandService gradeDistributionRuleCommandService;

    //등급 배분 정책 등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerDistributionRule(
            @PathVariable Long companyId,
            @PathVariable Long gradeId,
            @RequestBody RegisterGradeDistributionRuleRequestDto dto
    ) {
        gradeDistributionRuleCommandService.registerDistributionRule(
                companyId,
                gradeId,
                dto
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //등급 배분 정책 수정
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateDistributionRule(
            @PathVariable Long companyId,
            @PathVariable Long gradeId,
            @RequestBody RegisterGradeDistributionRuleRequestDto dto
    ) {
        gradeDistributionRuleCommandService.updateDistributionRule(
                companyId,
                gradeId,
                dto
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //등급 배분 정책 삭제
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteDistributionRule(
            @PathVariable Long companyId,
            @PathVariable Long gradeId
    ) {
        gradeDistributionRuleCommandService.deleteDistributionRule(
                companyId,
                gradeId
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
