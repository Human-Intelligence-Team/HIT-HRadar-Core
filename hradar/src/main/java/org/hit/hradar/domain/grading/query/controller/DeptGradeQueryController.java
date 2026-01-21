package org.hit.hradar.domain.grading.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.DeptGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.service.DeptGradeQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dept-grades")
public class DeptGradeQueryController {

    private final DeptGradeQueryService deptGradeQueryService;


    /*팀 등급 부여 현황 조회 (부여/미부여 부서 포함)*/

    @GetMapping
    public ResponseEntity<ApiResponse<List<DeptGradeStatusResponseDto>>> getDeptGradeStatus(
            @RequestParam Long companyId,
            @RequestParam Long cycleId
    ) {

        List<DeptGradeStatusResponseDto> response =
                deptGradeQueryService.getDeptGradeStatusList(companyId, cycleId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
