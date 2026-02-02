package org.hit.hradar.domain.rolePermission.command.application.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.command.application.dto.CreateRoleRequest;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdateRoleRequest;
import org.hit.hradar.domain.rolePermission.command.application.service.RoleCommandService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Role Command", description = "시스템 역할(Role) 생성/수정/삭제 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleCommandController {

  private final RoleCommandService roleCommandService;

  @Operation(summary = "새 역할 생성", description = "사급별/직무별 커스텀 역할을 새롭게 정의하고 권한을 부여합니다.")
  @PostMapping
  public ResponseEntity<ApiResponse<Long>> create(
      @RequestBody CreateRoleRequest request,
      @CurrentUser AuthUser authUser) {
    Long roleId = roleCommandService.createCustomRole(authUser, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(roleId));
  }

  @Operation(summary = "역할 정보 및 권한 수정", description = "기존 역할의 명칭이나 연결된 권한 목록을 업데이트합니다.")
  @PutMapping("/{roleId}")
  public ResponseEntity<ApiResponse<Void>> update(
      @PathVariable Long roleId,
      @RequestBody UpdateRoleRequest request,
      @CurrentUser AuthUser authUser) {
    roleCommandService.updateRole(authUser, roleId, request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @Operation(summary = "역할 삭제", description = "시스템에서 더 이상 사용하지 않는 역할을 삭제 처리합니다.")
  @DeleteMapping("/{roleId}")
  public ResponseEntity<ApiResponse<Void>> delete(
      @PathVariable Long roleId,
      @CurrentUser AuthUser authUser) {
    roleCommandService.deleteRole(authUser, roleId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}