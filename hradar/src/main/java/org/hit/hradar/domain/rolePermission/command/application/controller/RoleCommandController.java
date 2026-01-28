package org.hit.hradar.domain.rolePermission.command.application.controller;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleCommandController {

  private final RoleCommandService roleCommandService;

  @PostMapping
  public ResponseEntity<ApiResponse<Long>> create(
      @RequestBody CreateRoleRequest request,
      @CurrentUser AuthUser authUser
  ) {
    Long roleId = roleCommandService.createCustomRole(authUser, request);
    return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.success(roleId));
  }

  @PutMapping("/{roleId}")
  public ResponseEntity<ApiResponse<Void>> update(
      @PathVariable Long roleId,
      @RequestBody UpdateRoleRequest request,
      @CurrentUser AuthUser authUser
  ) {
    roleCommandService.updateRole(authUser, roleId, request);
    return ResponseEntity.ok(ApiResponse.success(null));
  }

  @DeleteMapping("/{roleId}")
  public ResponseEntity<ApiResponse<Void>> delete(
      @PathVariable Long roleId,
      @CurrentUser AuthUser authUser
  ) {
    roleCommandService.deleteRole(authUser, roleId);
    return ResponseEntity.ok(ApiResponse.success(null));
  }
}