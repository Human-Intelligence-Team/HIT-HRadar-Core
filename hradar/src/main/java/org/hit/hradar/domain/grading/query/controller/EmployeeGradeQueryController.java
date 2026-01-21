package org.hit.hradar.domain.grading.query.controller;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.grading.query.dto.response.EmployeeGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.service.EmployeeGradeQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/individual-grades")
public class EmployeeGradeQueryController {

    private final EmployeeGradeQueryService employeeGradeQueryService;

    /*사원별 등급 부여 현황*/
    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<EmployeeGradeStatusResponseDto>>> getEmployeeGrades(
            @RequestParam Long companyId,
            @RequestParam Long cycleId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        employeeGradeQueryService.getEmployeeGradeStatusList(
                                companyId,
                                cycleId
                        )
                )
        );
    }
}
