package org.hit.hradar.domain.rolePermission.command.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.RoleErrorCode;
import org.hit.hradar.domain.rolePermission.command.application.dto.CreateRoleRequest;
import org.hit.hradar.domain.rolePermission.command.application.dto.UpdateRoleRequest;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.RolePermission;
import org.hit.hradar.domain.rolePermission.command.infrastructure.PermissionJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RolePermissionJpaRepository;
import org.hit.hradar.global.dto.AuthUser;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleCommandService {

  private final RoleJpaRepository roleJpaRepository;
  private final RolePermissionJpaRepository rolePermissionJpaRepository;
  private final PermissionJpaRepository permissionJpaRepository;

  // 커스텀 역할 생성
  @Transactional
  public Long createCustomRole(AuthUser authUser, CreateRoleRequest req) {
    Long comId = authUser.companyId();

    validatePermIds(req.getPermIds());

    Role role = Role.createCustomRole(comId, req.getName());
    Role saved = roleJpaRepository.save(role);

    // 권한 매핑(replace 개념으로 insert)
    replaceRolePermissions(saved.getRoleId(), req.getPermIds());

    return saved.getRoleId();
  }

  // 역할 수정, company 격리 필수, userRole=USER는 기본 역할(isSystem=true) 수정 불가
  @Transactional
  public void updateRole(AuthUser authUser, Long roleId, UpdateRoleRequest req) {
    Long comId = authUser.companyId();

    validatePermIds(req.getPermIds());

    Role role = roleJpaRepository.findById(roleId)
        .orElseThrow(() -> new BusinessException(RoleErrorCode.ROLE_NOT_FOUND));

    // 회사 격리 + 삭제 여부
    if (!role.getComId().equals(comId) || role.isDeleted()) {
      throw new BusinessException(RoleErrorCode.ROLE_NOT_FOUND);
    }

    // userRole=USER는 기본 역할 수정 불가 (정책 변경: 권한 매핑을 위해 수정 허용)
    // if (role.getIsSystem() == 'Y') {
    // throw new BusinessException(RoleErrorCode.SYSTEM_ROLE_CANNOT_UPDATE);
    // }

    // 권한 매핑은 replace 방식: 전체 삭제 후 재삽입
    rolePermissionJpaRepository.deleteByIdRoleId(roleId);
    replaceRolePermissions(roleId, req.getPermIds());
  }

  // 역할 삭제(soft delete) - userRole=USER는 기본 역할(isSystem=true) 삭제 불가
  @Transactional
  public void deleteRole(AuthUser authUser, Long roleId) {
    Long comId = authUser.companyId();

    Role role = roleJpaRepository.findById(roleId)
        .orElseThrow(() -> new BusinessException(RoleErrorCode.ROLE_NOT_FOUND));

    if (!role.getComId().equals(comId) || role.isDeleted()) {
      throw new BusinessException(RoleErrorCode.ROLE_NOT_FOUND);
    }

    if (role.getIsSystem() == 'Y') {
      throw new BusinessException(RoleErrorCode.SYSTEM_ROLE_CANNOT_DELETE);
    }

    role.changeDeleted();
    rolePermissionJpaRepository.deleteByIdRoleId(roleId);
  }

  private void replaceRolePermissions(Long roleId, List<Long> permIds) {
    if (permIds == null || permIds.isEmpty())
      return;

    Set<Long> unique = new HashSet<>(permIds);

    List<RolePermission> mappings = unique.stream()
        .map(permId -> RolePermission.of(roleId, permId))
        .toList();

    rolePermissionJpaRepository.saveAll(mappings);
  }

  private void validatePermIds(List<Long> permIds) {
    if (permIds == null || permIds.isEmpty())
      return;

    List<Permission> perms = permissionJpaRepository.findAllByPermIdIn(permIds);
    if (perms.size() != new HashSet<>(permIds).size()) {
      throw new BusinessException(RoleErrorCode.INVALID_PERMISSION_ID);
    }
  }
}