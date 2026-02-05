package org.hit.hradar.domain.rolePermission.command.application.service;

import org.hit.hradar.domain.rolePermission.command.application.dto.CreatePermissionRequest;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdatePermissionRequest;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.hit.hradar.domain.rolePermission.command.infrastructure.PermissionJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PermissionCommandServiceTest {

    @InjectMocks
    private PermissionCommandService permissionCommandService;

    @Mock
    private PermissionJpaRepository permissionRepository;

    @Test
    @DisplayName("권한 생성 성공")
    void createPermission_Success() {
        // given
        CreatePermissionRequest request = new CreatePermissionRequest(null, "USER_MANAGE", "User Management", "/users",
                "Desc");

        Permission savedPerm = Permission.builder().permId(1L).permKey("USER_MANAGE").build();

        given(permissionRepository.existsByPermKeyAndIsDeleted("USER_MANAGE", 'N')).willReturn(false);
        given(permissionRepository.save(any(Permission.class))).willReturn(savedPerm);

        // when
        Long permId = permissionCommandService.createPermission(request);

        // then
        assertThat(permId).isEqualTo(1L);
        verify(permissionRepository).save(any(Permission.class));
    }

    @Test
    @DisplayName("권한 정보 수정 성공")
    void updatePermission_Success() {
        // given
        Long permId = 1L;
        UpdatePermissionRequest request = new UpdatePermissionRequest("Updated Permission", "/new-path", "New Desc");

        Permission permission = Permission.builder()
                .permId(permId)
                .name("Old Permission")
                .build();

        given(permissionRepository.findById(permId)).willReturn(Optional.of(permission));

        // when
        permissionCommandService.updatePermission(permId, request);

        // then
        assertThat(permission.getName()).isEqualTo("Updated Permission");
    }

    @Test
    @DisplayName("권한 삭제 성공")
    void deletePermission_Success() {
        // given
        Long permId = 1L;
        Permission permission = Permission.builder().permId(permId).build();

        given(permissionRepository.findById(permId)).willReturn(Optional.of(permission));

        // when
        permissionCommandService.deletePermission(permId);

        // then
        assertThat(permission.getIsDeleted()).isEqualTo('Y');
    }
}
