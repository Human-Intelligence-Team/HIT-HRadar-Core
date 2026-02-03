package org.hit.hradar.domain.grading.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Grading - Individual Status", description = "사원 개인별 등급 부여 현황 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/individual-grades")
public class EmployeeGradeQueryController {

        private final EmployeeGradeQueryService employeeGradeQueryService;
        private final EmployeeRepository employeeRepository;

        /* 사원별 등급 부여 현황 */
        @Operation(summary = "사원 등급 현황 조회", description = "특정 평가 주기에 해당 부서 사원들에게 부여된 등급 현황을 조회합니다.")
        @GetMapping("/employees")
        public ResponseEntity<ApiResponse<List<EmployeeGradeStatusResponseDto>>> getEmployeeGrades(
                        @CurrentUser AuthUser authUser,
                        @RequestParam Long cycleId) {

                Employee emp = employeeRepository.findById(authUser.employeeId()).orElseThrow();
                Long deptId = emp.getDeptId();

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                employeeGradeQueryService.getEmployeeGradeStatusList(
                                                                authUser.companyId(),
                                                                deptId,
                                                                cycleId)));
        }
}
