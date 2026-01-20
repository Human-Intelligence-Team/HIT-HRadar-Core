package org.hit.hradar.domain.rolePermission.command.infrastructure;

import org.hit.hradar.domain.rolePermission.command.domain.aggregate.RoleEmployee;
import org.hit.hradar.domain.rolePermission.command.domain.repository.RoleEmpRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEmpJpaRepository extends JpaRepository<RoleEmployee, Long> ,
    RoleEmpRepository {
  boolean existsByEmpIdAndRoleId(Long empId, Long roleId);
}
