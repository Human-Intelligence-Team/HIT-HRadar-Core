package org.hit.hradar.domain.rolePermission.command.infrastructure;

import java.util.List;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.RolePermission;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.RolePermission.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionJpaRepository
    extends JpaRepository<RolePermission, RolePermissionId> {

  void deleteByIdRoleId(Long roleId);

  List<RolePermission> findAllByIdRoleId(Long roleId);
}