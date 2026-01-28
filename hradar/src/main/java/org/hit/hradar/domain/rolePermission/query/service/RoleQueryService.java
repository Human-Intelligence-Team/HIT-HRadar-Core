package org.hit.hradar.domain.rolePermission.query.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.RoleErrorCode;
import org.hit.hradar.domain.rolePermission.query.dto.RoleListRequest;
import org.hit.hradar.domain.rolePermission.query.dto.PermissionResponse;
import org.hit.hradar.domain.rolePermission.query.dto.RoleResponse;
import org.hit.hradar.domain.rolePermission.query.mapper.RoleMapper;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleQueryService {

  private final RoleMapper roleMapper;

  @Transactional(readOnly = true)
  public List<RoleResponse> getRoles(Long comId, RoleListRequest req) {
    List<RoleResponse> roles = roleMapper.selectRoles(comId, req);

    for (RoleResponse role : roles) {
      role.setPermissions(
          roleMapper.selectRolePermissions(role.getRoleId())
      );
    }
    return roles;
  }

  @Transactional(readOnly = true)
  public RoleResponse getRole(Long comId, Long roleId) {
    RoleResponse role = roleMapper.selectRole(comId, roleId);
    if (role == null) {
      throw new BusinessException(RoleErrorCode.ROLE_NOT_FOUND);
    }

    role.setPermissions(
        roleMapper.selectRolePermissions(roleId)
    );
    return role;
  }

  @Transactional(readOnly = true)
  public List<PermissionResponse> getAllPermissions() {
    return roleMapper.selectAllPermissions();
  }
}
