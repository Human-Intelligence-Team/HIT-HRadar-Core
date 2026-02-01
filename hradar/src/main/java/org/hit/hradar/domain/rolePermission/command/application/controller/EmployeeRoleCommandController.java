package org.hit.hradar.domain.rolePermission.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdateEmployeeRolesRequest;
import org.hit.hradar.domain.rolePermission.command.application.service.EmployeeRoleAssignmentApplicationService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeRoleCommandController {

    private final EmployeeRoleAssignmentApplicationService roleAssignmentService;

    @PutMapping("/{empId}/roles")
    public ResponseEntity<ApiResponse<Void>> updateEmployeeRoles(
            @PathVariable Long empId,
            @RequestBody UpdateEmployeeRolesRequest request,
            @CurrentUser AuthUser authUser) {
        roleAssignmentService.updateEmployeeRoles(authUser.companyId(), empId, request.getRoleIds());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
