package org.hit.hradar.domain.rolePermission.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Permission Command", description = "시스템 상세 권한 관리 API (Admin 전용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionCommandController {

    private final PermissionCommandService permissionCommandService;

    @Operation(summary = "새 권한 항목 생성", description = "시스템 내에서 사용될 새로운 권한(기능 단위)을 등록합니다. (Admin 권한 필요)")
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

    @Operation(summary = "권한 정보 수정", description = "기존 권한의 명칭이나 설명을 수정합니다. (Admin 권한 필요)")
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

    @Operation(summary = "권한 삭제", description = "시스템 기능 변경 등으로 인해 불필요해진 권한 항목을 삭제합니다. (Admin 권한 필요)")
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
