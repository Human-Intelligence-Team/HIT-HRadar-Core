package org.hit.hradar.domain.grading.query.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("individual-grades")
public class IndividualGradeQueryController {

    private final IndividualGradeQueryService  individualGradeQueryService;

    @GetMapping("/my")
    public ResponseEntity<ApiResponse<MyIndividualGradeResponseDto>> getMyGrade(
            @CurrentUser AuthUser authUser,
            @RequestParam Long cycleId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        individualGradeQueryService.getMyIndividualGrade(
                                authUser.employeeId(),
                                cycleId
                        )
                )
        );
    }
}
