package org.hit.hradar.domain.grading.query.controller;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/grade-objections")
public class GradeObjectionQueryController {

    private final GradeObjectionQueryService gradeObjectionQueryService;
    private final EmployeeRepository employeeRepository;

    //개인 등급 선택시 이의제기 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<GradeObjectionResponseDto>>> getObjections(
            @RequestParam Long individualGradeId
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        gradeObjectionQueryService.getObjectionsByIndividualGrade(
                                individualGradeId
                        )
                )
        );
    }


    //부서별 이의제기 조회
    @GetMapping("/departments")
    public ResponseEntity<ApiResponse<List<GradeObjectionAdminResponseDto>>> getDeptObjections(
            @CurrentUser AuthUser authUser
            ) {
        Long empId = authUser.employeeId();
        Employee emp = employeeRepository.findById(empId).orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));
        Long deptId = emp.getDeptId();

        return ResponseEntity.ok(
                ApiResponse.success(
                        gradeObjectionQueryService.getObjectionsByDepartment(authUser.companyId(), deptId)
                )
        );
    }

    //전체사원 이의제기 조회
    @GetMapping("/companies/{companyId}")
    public ResponseEntity<ApiResponse<List<GradeObjectionAdminResponseDto>>> getAllObjections(
            @CurrentUser AuthUser authUser
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        gradeObjectionQueryService.getAllObjections(authUser.companyId())
                )
        );
    }

}
