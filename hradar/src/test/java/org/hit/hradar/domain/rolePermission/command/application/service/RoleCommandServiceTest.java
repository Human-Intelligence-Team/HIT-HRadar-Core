package org.hit.hradar.domain.rolePermission.command.application.service;

import org.hit.hradar.domain.rolePermission.command.application.dto.CreateRoleRequest;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdateRoleRequest;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.infrastructure.PermissionJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RolePermissionJpaRepository;
import org.hit.hradar.global.dto.AuthUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RoleCommandServiceTest {

    @InjectMocks
    private RoleCommandService roleCommandService;

    @Mock
    private RoleJpaRepository roleJpaRepository;

    @Mock
    private RolePermissionJpaRepository rolePermissionJpaRepository;

    @Mock
    private PermissionJpaRepository permissionJpaRepository;

    @Test
    @DisplayName("커스텀 역할 생성 성공")
    void createCustomRole_Success() {
        // given
        AuthUser authUser = new AuthUser(1L, "admin", 1L, 1L);
        CreateRoleRequest req = new CreateRoleRequest("Custom Role", List.of(1L, 2L));

        Role savedRole = Role.createCustomRole(1L, "Custom Role");
        Permission perm1 = Permission.builder().permId(1L).build();
        Permission perm2 = Permission.builder().permId(2L).build();

        given(roleJpaRepository.save(any(Role.class))).willReturn(savedRole);
        given(permissionJpaRepository.findAllByPermIdIn(anyList())).willReturn(List.of(perm1, perm2));

        // when
        roleCommandService.createCustomRole(authUser, req);

        // then
        verify(roleJpaRepository).save(any(Role.class));
        verify(rolePermissionJpaRepository).saveAll(anyList());
    }

    @Test
    @DisplayName("역할 삭제 성공")
    void deleteRole_Success() {
        // given
        AuthUser authUser = new AuthUser(1L, "admin", 1L, 1L);
        Long roleId = 10L;
        Role role = Role.builder()
                .roleId(roleId)
                .comId(1L)
                .isSystem('N')
                .build();

        given(roleJpaRepository.findById(roleId)).willReturn(Optional.of(role));

        // when
        roleCommandService.deleteRole(authUser, roleId);

        // then
        assertThat(role.getIsDeleted()).isEqualTo('Y');
        verify(rolePermissionJpaRepository).deleteByIdRoleId(roleId);
    }
}
