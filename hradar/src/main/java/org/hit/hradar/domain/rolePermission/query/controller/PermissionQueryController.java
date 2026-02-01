package org.hit.hradar.domain.rolePermission.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.query.dto.PermissionResponse;
import org.hit.hradar.domain.rolePermission.query.service.PermissionQueryService;
import org.hit.hradar.global.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionQueryController {

    private final PermissionQueryService permissionQueryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        List<PermissionResponse> response = permissionQueryService.getAllPermissions();
        return ResponseEntity.ok(ApiResponse.success(response));
    }
}
