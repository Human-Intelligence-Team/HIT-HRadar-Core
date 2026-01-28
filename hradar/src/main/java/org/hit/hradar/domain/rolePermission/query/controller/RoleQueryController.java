package org.hit.hradar.domain.rolePermission.query.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.query.dto.RoleListRequest;
import org.hit.hradar.domain.rolePermission.query.dto.PermissionResponse;
import org.hit.hradar.domain.rolePermission.query.dto.RoleResponse;
import org.hit.hradar.domain.rolePermission.query.service.RoleQueryService;
import org.hit.hradar.global.aop.CurrentUser;
import org.hit.hradar.global.dto.ApiResponse;
import org.hit.hradar.global.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleQueryController {

  private final RoleQueryService roleQueryService;

  @GetMapping
  public ResponseEntity<ApiResponse<List<RoleResponse>>> list(
      RoleListRequest req,
      @CurrentUser AuthUser authUser
  ) {
    return ResponseEntity.ok(
        ApiResponse.success(
            roleQueryService.getRoles(authUser.companyId(), req)
        )
    );
  }

  @GetMapping("/{roleId}")
  public ResponseEntity<ApiResponse<RoleResponse>> detail(
      @PathVariable Long roleId,
      @CurrentUser AuthUser authUser
  ) {
    return ResponseEntity.ok(
        ApiResponse.success(
            roleQueryService.getRole(authUser.companyId(), roleId)
        )
    );
  }

  @GetMapping("/permissions")
  public ResponseEntity<ApiResponse<List<PermissionResponse>>> permissions() {
    return ResponseEntity.ok(
        ApiResponse.success(roleQueryService.getAllPermissions())
    );
  }
}
