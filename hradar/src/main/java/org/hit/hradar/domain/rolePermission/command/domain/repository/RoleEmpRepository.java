package org.hit.hradar.domain.rolePermission.command.domain.repository;


import org.hit.hradar.domain.rolePermission.command.domain.aggregate.EmployeeRole;

public interface RoleEmpRepository {
  boolean existsByEmpIdAndRoleId(Long empId, Long roleId);

}
