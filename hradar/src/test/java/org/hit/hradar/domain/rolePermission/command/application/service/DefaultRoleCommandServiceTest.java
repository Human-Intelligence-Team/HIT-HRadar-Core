package org.hit.hradar.domain.rolePermission.command.application.service;

import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.RolePermission;
import org.hit.hradar.domain.rolePermission.command.infrastructure.PermissionJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RolePermissionJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DefaultRoleCommandServiceTest {

    @InjectMocks
    private DefaultRoleCommandService defaultRoleCommandService;

    @Mock
    private RoleJpaRepository roleRepository;

    @Mock
    private PermissionJpaRepository permissionRepository;

    @Mock
    private RolePermissionJpaRepository rolePermissionRepository;

    @Test
    @DisplayName("기본 역할 및 권한 매핑 보장 로직 검증")
    void ensureDefaults_Success() {
        // given
        Long comId = 1L;
        Role role = Role.builder().roleId(1L).comId(comId).roleKey("EMPLOYEE").build();
        Permission permission = Permission.builder().permId(1L).permKey("BASIC_MENU").build();

        given(roleRepository.findByComIdAndRoleKey(any(), anyString())).willReturn(Optional.of(role));
        given(permissionRepository.findByPermKey(anyString())).willReturn(Optional.of(permission));
        given(rolePermissionRepository.findAllByIdRoleId(any())).willReturn(Collections.emptyList());

        // when
        defaultRoleCommandService.ensureDefaults(comId);

        // then
        verify(rolePermissionRepository, atLeastOnce()).save(any(RolePermission.class));
    }
}
