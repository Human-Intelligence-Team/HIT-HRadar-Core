package org.hit.hradar.domain.rolePermission.command.domain.repository;


public interface RoleEmpRepository {
  boolean existsByEmpIdAndRoleId(Long empId, Long roleId);
}
