package org.hit.hradar.domain.rolePermission.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Role Query", description = "사용자 역할(Role) 및 접근 권한 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleQueryController {

  private final RoleQueryService roleQueryService;

  @Operation(summary = "역할 목록 조회", description = "시스템에 정의된 모든 역할 목록을 필터 조건에 따라 조회합니다.")
  @GetMapping
  public ResponseEntity<ApiResponse<List<RoleResponse>>> list(
      RoleListRequest req,
      @CurrentUser AuthUser authUser) {
    return ResponseEntity.ok(
        ApiResponse.success(
            roleQueryService.getRoles(authUser.companyId(), req)));
  }

  @Operation(summary = "역할 상세 조회", description = "특정 역할의 세부 정보와 연결된 권한들을 조회합니다.")
  @GetMapping("/{roleId}")
  public ResponseEntity<ApiResponse<RoleResponse>> detail(
      @PathVariable Long roleId,
      @CurrentUser AuthUser authUser) {
    return ResponseEntity.ok(
        ApiResponse.success(
            roleQueryService.getRole(authUser.companyId(), roleId)));
  }

  @Operation(summary = "전체 권한 목록 조회", description = "시스템에서 사용 가능한 모든 권한 상수를 조회합니다.")
  @GetMapping("/permissions")
  public ResponseEntity<ApiResponse<List<PermissionResponse>>> permissions() {
    return ResponseEntity.ok(
        ApiResponse.success(roleQueryService.getAllPermissions()));
  }

  @Operation(summary = "내 권한 목록 조회", description = "현재 로그인한 사용자가 보유한 모든 권한 목록을 조회합니다.")
  @GetMapping("/my-permissions")
  public ResponseEntity<ApiResponse<List<String>>> getMyPermissions(
      @CurrentUser AuthUser authUser) {
    return ResponseEntity.ok(
        ApiResponse.success(roleQueryService.getMyPermissions(authUser)));
  }
}
