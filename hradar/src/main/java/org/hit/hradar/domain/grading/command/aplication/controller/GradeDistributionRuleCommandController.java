package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterGradeDistributionRuleRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.GradeDistributionRuleCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/grades/{gradeId}/distribution-rule")
public class GradeDistributionRuleCommandController {

    private final GradeDistributionRuleCommandService gradeDistributionRuleCommandService;

    //등급 배분 정책 등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> registerDistributionRule(
            @CurrentUser AuthUser authUser,
            @PathVariable Long gradeId,
            @RequestBody RegisterGradeDistributionRuleRequestDto dto
    ) {
        gradeDistributionRuleCommandService.registerDistributionRule(
                authUser.companyId(),
                gradeId,
                dto
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //등급 배분 정책 수정
    @PutMapping
    public ResponseEntity<ApiResponse<Void>> updateDistributionRule(
            @CurrentUser AuthUser authUser,
            @PathVariable Long gradeId,
            @RequestBody RegisterGradeDistributionRuleRequestDto dto
    ) {
        gradeDistributionRuleCommandService.updateDistributionRule(
                authUser.companyId(),
                gradeId,
                dto
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //등급 배분 정책 삭제
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteDistributionRule(
            @CurrentUser AuthUser authUser,
            @PathVariable Long gradeId
    ) {
        gradeDistributionRuleCommandService.deleteDistributionRule(
                authUser.companyId(),
                gradeId
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
