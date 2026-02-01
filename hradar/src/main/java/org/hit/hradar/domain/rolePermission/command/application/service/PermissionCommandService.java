package org.hit.hradar.domain.rolePermission.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.RoleErrorCode;
import org.hit.hradar.domain.rolePermission.command.application.dto.CreatePermissionRequest;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdatePermissionRequest;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.hit.hradar.domain.rolePermission.command.infrastructure.PermissionJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PermissionCommandService {

    private final PermissionJpaRepository permissionRepository;

    @Transactional
    public Long createPermission(CreatePermissionRequest request) {
        if (permissionRepository.existsByPermKeyAndIsDeleted(request.getPermKey(), 'N')) {
            throw new BusinessException(RoleErrorCode.DUPLICATE_PERMISSION_KEY);
        }

        Permission permission = Permission.builder()
                .parentPermId(request.getParentPermId())
                .permKey(request.getPermKey())
                .name(request.getName())
                .routePath(request.getRoutePath())
                .description(request.getDescription())
                .isDeleted('N')
                .build();

        return permissionRepository.save(permission).getPermId();
    }

    @Transactional
    public void updatePermission(Long permId, UpdatePermissionRequest request) {
        Permission permission = permissionRepository.findById(permId)
                .orElseThrow(() -> new BusinessException(RoleErrorCode.PERMISSION_NOT_FOUND));

        // 업데이트 메서드 호출
        permission.updateInfo(request.getName(), request.getRoutePath(), request.getDescription());
    }

    @Transactional
    public void deletePermission(Long permId) {
        Permission permission = permissionRepository.findById(permId)
                .orElseThrow(() -> new BusinessException(RoleErrorCode.PERMISSION_NOT_FOUND));

        permission.delete(); // Soft Delete 처리
    }
}
