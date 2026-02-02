package org.hit.hradar.domain.rolePermission.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdateEmployeeRolesRequest;
import org.hit.hradar.domain.rolePermission.command.application.service.EmployeeRoleAssignmentApplicationService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Employee Role Assignment", description = "사원 개별 역할(Role) 배정 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeRoleCommandController {

    private final EmployeeRoleAssignmentApplicationService roleAssignmentService;

    @Operation(summary = "사원 역할 수정", description = "특정 사원에게 할당된 시스템 역할(Role) 목록을 업데이트합니다.")
    @PutMapping("/{empId}/roles")
    public ResponseEntity<ApiResponse<Void>> updateEmployeeRoles(
            @PathVariable Long empId,
            @RequestBody UpdateEmployeeRolesRequest request,
            @CurrentUser AuthUser authUser) {
        roleAssignmentService.updateEmployeeRoles(authUser.companyId(), empId, request.getRoleIds());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
