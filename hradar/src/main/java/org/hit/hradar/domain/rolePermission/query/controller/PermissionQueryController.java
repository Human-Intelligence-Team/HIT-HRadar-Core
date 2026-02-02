package org.hit.hradar.domain.rolePermission.query.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.query.dto.PermissionResponse;
import org.hit.hradar.domain.rolePermission.query.service.PermissionQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Permission Query", description = "시스템 전체 권한 정보 조회 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionQueryController {

    private final PermissionQueryService permissionQueryService;

    @Operation(summary = "전체 권한 조회", description = "데이터베이스에 등록된 모든 시스템 권한 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        List<PermissionResponse> response = permissionQueryService.getAllPermissions();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
