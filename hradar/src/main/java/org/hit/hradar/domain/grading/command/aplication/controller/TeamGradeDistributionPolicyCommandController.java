package org.hit.hradar.domain.grading.command.aplication.controller;


import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterTeamGradeDistributionPolicyRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.UpdateTeamGradeDistributionPolicyRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.TeamGradeDistributionPolicyCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/distribution-policies")
public class TeamGradeDistributionPolicyCommandController {
    private final TeamGradeDistributionPolicyCommandService commandService;


    @PostMapping("/{teamGradeId}")
    public ResponseEntity<ApiResponse<Void>> register(
            @CurrentUser AuthUser authUser,
            @PathVariable Long teamGradeId,
            @RequestBody RegisterTeamGradeDistributionPolicyRequestDto request
    ) {
        commandService.register(
                authUser.companyId(),
                teamGradeId,
                request
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }


    @PutMapping("/{policyId}")
    public ResponseEntity<ApiResponse<Void>> update(
            @CurrentUser AuthUser authUser,
            @PathVariable Long policyId,
            @RequestBody UpdateTeamGradeDistributionPolicyRequestDto request
    ) {
        commandService.update(
                authUser.companyId(),
                policyId,
                request
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }


    @DeleteMapping("/{policyId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @CurrentUser AuthUser authUser,
            @PathVariable Long policyId
    ) {
        commandService.delete(
                authUser.companyId(),
                policyId
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
