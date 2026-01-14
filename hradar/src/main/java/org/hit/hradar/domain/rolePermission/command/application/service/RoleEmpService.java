package org.hit.hradar.domain.rolePermission.command.application.service;

import org.hit.hradar.domain.rolePermission.command.domain.aggregate.RoleEmployee;
import org.hit.hradar.domain.rolePermission.command.domain.repository.RoleEmpRepository;

public class RoleEmpService {

  private final RoleEmpRepository roleEmpRepository;

  public void assign(Long empId, Long roleId) {
    roleEmpRepository.save(
        RoleEmployee.builder()
            .empId(empId)
            .roleId(roleId)
            .build()
    );
  }
}
