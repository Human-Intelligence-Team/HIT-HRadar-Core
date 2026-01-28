package org.hit.hradar.domain.rolePermission.command.domain.repository;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;

public interface RoleRepository {
  Optional<Role> findByComIdAndRoleKey(Long comId, String roleKey);
  List<Role> findAllByComId(Long comId);

}