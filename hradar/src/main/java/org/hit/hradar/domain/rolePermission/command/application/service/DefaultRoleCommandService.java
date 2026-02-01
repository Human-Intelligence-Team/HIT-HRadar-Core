package org.hit.hradar.domain.rolePermission.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.RolePermission;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.SystemRoleDefinition;
import org.hit.hradar.domain.rolePermission.command.infrastructure.PermissionJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RolePermissionJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultRoleCommandService {

  private final RoleJpaRepository roleRepository;
  private final PermissionJpaRepository permissionRepository;
  private final RolePermissionJpaRepository rolePermissionRepository;

  @Transactional
  public void ensureDefaults(Long comId) {
    for (SystemRoleDefinition def : SystemRoleDefinition.values()) {
      // 1. Role 생성 (없을 경우)
      Role role = roleRepository.findByComIdAndRoleKey(comId, def.getRoleKey())
          .orElseGet(() -> roleRepository.save(
              Role.createSystemRole(comId, def.getRoleKey(), def.getName())));

      // 2. 권한 Seed Check & Mapping Check
      for (String permKey : def.getDefaultPermKeys()) {
        Permission permission = permissionRepository.findByPermKey(permKey)
            .orElseGet(() -> permissionRepository.save(
                Permission.builder()
                    .permKey(permKey)
                    .name(permKey) // 초기 이름은 키와 동일하게 설정 (관리자가 수정 가능)
                    .description("System Default Permission")
                    .isDeleted('N')
                    .build()));

        // 3. RolePermission 매핑 (없을 경우)
        boolean mappingExists = rolePermissionRepository.findAllByIdRoleId(role.getRoleId()).stream()
            .anyMatch(rp -> rp.getId().getPermId().equals(permission.getPermId()));

        if (!mappingExists) {
          rolePermissionRepository.save(RolePermission.of(role.getRoleId(), permission.getPermId()));
        }
      }
    }
  }
}
