package org.hit.hradar.domain.grading.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.grading.command.domain.aggregate.DeptGrade;
import org.hit.hradar.domain.grading.command.domain.aggregate.Grade;
import org.hit.hradar.domain.grading.command.domain.repository.DeptGradeRepository;
import org.hit.hradar.domain.grading.command.domain.repository.GradeRepository;
import org.hit.hradar.domain.grading.query.dto.response.DeptGradeStatusResponseDto;
import org.hit.hradar.domain.grading.query.dto.response.MyDeptGradeResponseDto;
import org.hit.hradar.domain.grading.query.service.DeptGradeQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Grading - Department Status", description = "부서별 등급 부여 현황 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/dept-grades")
public class DeptGradeQueryController {

    private final DeptGradeQueryService deptGradeQueryService;
    private final EmployeeRepository employeeRepository;
    private final DeptGradeRepository deptGradeRepository;
    private final GradeRepository gradeRepository;

    /* 팀 등급 부여 현황 조회 (부여/미부여 부서 포함) */

    @Operation(summary = "부서별 등급 현황 목록 조회", description = "회사 내 모든 부서의 등급 부여 여부 및 세부 현황 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<DeptGradeStatusResponseDto>>> getDeptGradeStatus(
            @CurrentUser AuthUser authUser,
            @RequestParam Long cycleId) {
        List<DeptGradeStatusResponseDto> response = deptGradeQueryService.getDeptGradeStatusList(authUser.companyId(),
                cycleId);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @Operation(summary = "내 부서 등급 조회", description = "로그인한 사용자가 속한 부서에 최종 확정된 등급 정보를 조회합니다.")
    @GetMapping("/my/dept")
    @ResponseBody
    public ResponseEntity<ApiResponse<MyDeptGradeResponseDto>> getDeptGrade(
            @CurrentUser AuthUser authUser) {
        Employee emp = employeeRepository.findById(authUser.employeeId()).orElseThrow();
        Long deptId = emp.getDeptId();

        DeptGrade deptGrade =
                deptGradeRepository.findByDepartmentId(deptId).orElse(null);

        if (deptGrade == null) {
            return ResponseEntity.ok(ApiResponse.success(null));
        }
        Grade deptgrade = gradeRepository.findById(deptGrade.getGradeId()).orElseThrow();

        MyDeptGradeResponseDto response = new MyDeptGradeResponseDto();
        response.setGradeName(deptgrade.getGradeName());
        response.setGradeId(deptgrade.getGradeId());
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
