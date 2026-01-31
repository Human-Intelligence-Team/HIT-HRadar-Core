package org.hit.hradar.domain.grading.query.controller;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.grading.query.dto.response.EmployeeGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.service.EmployeeGradeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/individual-grades")
public class EmployeeGradeQueryController {

    private final EmployeeGradeQueryService employeeGradeQueryService;
    private final EmployeeRepository employeeRepository;

    /*사원별 등급 부여 현황*/
    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<EmployeeGradeStatusResponseDto>>> getEmployeeGrades(
            @CurrentUser AuthUser authUser,
            @RequestParam Long cycleId
    ) {

        Employee emp = employeeRepository.findById(authUser.employeeId()).orElseThrow();
        Long deptId = emp.getDeptId();

        return ResponseEntity.ok(
                ApiResponse.success(
                        employeeGradeQueryService.getEmployeeGradeStatusList(
                                authUser.companyId(),
                                deptId,
                                cycleId
                        )
                )
        );
    }
}
