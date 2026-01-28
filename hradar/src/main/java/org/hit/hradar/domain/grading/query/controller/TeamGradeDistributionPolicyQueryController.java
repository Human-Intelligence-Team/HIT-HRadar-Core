package org.hit.hradar.domain.grading.query.controller;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.TeamGradeDistributionPolicyDto;
import org.hit.hradar.domain.grading.query.service.TeamGradeDistributionPolicyQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/distribution-policies")
public class TeamGradeDistributionPolicyQueryController {
    private final TeamGradeDistributionPolicyQueryService queryService;

    /**
     * 팀 등급 기준 정책 목록 조회
     */
    @GetMapping("/team-grades/{teamGradeId}")
    public ResponseEntity<ApiResponse<List<TeamGradeDistributionPolicyDto>>> getPolicies(
            @CurrentUser AuthUser authUser,
            @PathVariable Long teamGradeId
    ) {
        List<TeamGradeDistributionPolicyDto> result =
                queryService.getPolicies(authUser.companyId(), teamGradeId);

        return ResponseEntity.ok(ApiResponse.success(result));
    }

    /**
     * 단건 정책 조회
     */
    @GetMapping("/team-grades/{teamGradeId}/member-grades/{memberGradeId}")
    public ResponseEntity<ApiResponse<TeamGradeDistributionPolicyDto>> getPolicy(
            @CurrentUser AuthUser authUser,
            @PathVariable Long teamGradeId,
            @PathVariable Long memberGradeId
    ) {
        TeamGradeDistributionPolicyDto result =
                queryService.getPolicy(
                        authUser.companyId(),
                        teamGradeId,
                        memberGradeId
                );

        return ResponseEntity.ok(ApiResponse.success(result));
    }
}
