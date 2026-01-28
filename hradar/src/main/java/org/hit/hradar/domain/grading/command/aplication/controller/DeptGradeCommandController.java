package org.hit.hradar.domain.grading.command.aplication.controller;

import lombok.RequiredArgsConstructor;

import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.grading.command.aplication.dto.request.AssignDeptGradeRequestDto;
import org.hit.hradar.domain.grading.command.aplication.dto.request.DeptGradeUpdateRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.DeptGradeCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dept-grades")
public class DeptGradeCommandController {
    private final DeptGradeCommandService deptGradeCommandService;
    private final EmployeeRepository employeeRepository;

    //등록
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> assignDeptGrade(
            @RequestBody AssignDeptGradeRequestDto request,
            @CurrentUser AuthUser authUser
    ) {
        deptGradeCommandService.assignDeptGrade(request, authUser.employeeId());

        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //수정
    @PutMapping("/{deptGradeId}")
    public ResponseEntity<ApiResponse<Void>> updateDeptGrade(
            @CurrentUser AuthUser authUser,
            @PathVariable Long deptGradeId,
            @RequestBody DeptGradeUpdateRequestDto request
    ) {
        deptGradeCommandService.updateDeptGrade(deptGradeId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //삭제
    @DeleteMapping("/{deptGradeId}")
    public ResponseEntity<ApiResponse<Void>> deleteDeptGrade(
            @PathVariable Long deptGradeId
    ) {
        deptGradeCommandService.deleteDeptGrade(deptGradeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //제출
    @PostMapping("/{deptGradeId}/submit")
    public ResponseEntity<ApiResponse<Void>> submitDeptGrade(
            @PathVariable Long deptGradeId
    ) {
        deptGradeCommandService.submitDeptGrade(deptGradeId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    //승인
    @PostMapping("/{deptGradeId}/approve")
    public ResponseEntity<ApiResponse<Void>> approveDeptGrade(
            @PathVariable Long deptGradeId,
            @CurrentUser AuthUser authUser
    ) {
        deptGradeCommandService.approveDeptGrade(
                deptGradeId,
                authUser.employeeId()
        );
        return ResponseEntity.ok(ApiResponse.success(null));
    }

}
