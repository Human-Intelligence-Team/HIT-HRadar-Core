package org.hit.hradar.domain.rolePermission.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.command.application.dto.CreatePermissionRequest;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdatePermissionRequest;
import org.hit.hradar.domain.rolePermission.command.application.service.PermissionCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.hit.hradar.domain.rolePermission.RoleErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionCommandController {

    private final PermissionCommandService permissionCommandService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPermission(
            @CurrentUser AuthUser authUser,
            @RequestBody CreatePermissionRequest request) {
        if (!"admin".equals(authUser.role())) {
            throw new BusinessException(RoleErrorCode.PERMISSION_DENIED);
        }
        Long permId = permissionCommandService.createPermission(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(permId));
    }

    @PatchMapping("/{permId}")
    public ResponseEntity<ApiResponse<Void>> updatePermission(
            @CurrentUser AuthUser authUser,
            @PathVariable Long permId,
            @RequestBody UpdatePermissionRequest request) {
        if (!"admin".equals(authUser.role())) {
            throw new BusinessException(RoleErrorCode.PERMISSION_DENIED);
        }
        permissionCommandService.updatePermission(permId, request);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    @DeleteMapping("/{permId}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(
            @CurrentUser AuthUser authUser,
            @PathVariable Long permId) {
        if (!"admin".equals(authUser.role())) {
            throw new BusinessException(RoleErrorCode.PERMISSION_DENIED);
        }
        permissionCommandService.deletePermission(permId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}
