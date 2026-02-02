package org.hit.hradar.domain.grading.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionAdminResponseDto;
import org.hit.hradar.domain.grading.query.dto.response.GradeObjectionResponseDto;
import org.hit.hradar.domain.grading.query.service.GradeObjectionQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Grading - Objection", description = "등급 이의제기 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/grade-objections")
public class GradeObjectionQueryController {

        private final GradeObjectionQueryService gradeObjectionQueryService;
        private final EmployeeRepository employeeRepository;

        // 개인 등급 선택시 이의제기 조회
        @Operation(summary = "개인 등급 이의제기 조회", description = "특정 개인 등급에 대해 제기된 이의신청 내용을 조회합니다.")
        @GetMapping
        public ResponseEntity<ApiResponse<List<GradeObjectionResponseDto>>> getObjections(
                        @RequestParam Long individualGradeId) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                gradeObjectionQueryService.getObjectionsByIndividualGrade(
                                                                individualGradeId)));
        }

        // 부서별 이의제기 조회
        @Operation(summary = "부서별 이의제기 목록 조회", description = "관리자/부서장이 확인 가능한 부서 내 전체 이의제기 목록을 조회합니다.")
        @GetMapping("/departments")
        public ResponseEntity<ApiResponse<List<GradeObjectionAdminResponseDto>>> getDeptObjections(
                        @CurrentUser AuthUser authUser) {
                Long empId = authUser.employeeId();
                Employee emp = employeeRepository.findById(empId)
                                .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));
                Long deptId = emp.getDeptId();

                return ResponseEntity.ok(
                                ApiResponse.success(
                                                gradeObjectionQueryService.getObjectionsByDepartment(
                                                                authUser.companyId(), deptId)));
        }

        // 전체사원 이의제기 조회
        @Operation(summary = "전체 사원 이의제기 조회", description = "회사 전체 사원들의 이의제기 현황을 조회합니다.")
        @GetMapping("/companies/{companyId}")
        public ResponseEntity<ApiResponse<List<GradeObjectionAdminResponseDto>>> getAllObjections(
                        @CurrentUser AuthUser authUser) {
                return ResponseEntity.ok(
                                ApiResponse.success(
                                                gradeObjectionQueryService.getAllObjections(authUser.companyId())));
        }

}
