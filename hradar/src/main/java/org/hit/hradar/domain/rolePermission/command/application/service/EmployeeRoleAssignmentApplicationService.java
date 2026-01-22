package org.hit.hradar.domain.rolePermission.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.RoleErrorCode;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.domain.policy.EmployeeRoleAssignmentPolicy;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeRoleAssignmentApplicationService {

  private final EmployeeRoleAssignmentPolicy employeeRoleAssignmentPolicy;
  private final DefaultRoleCommandService defaultRoleCommandService;
  private final RoleJpaRepository roleRepository;

  @Transactional
  public void assignForFirstEmployee(Long comId, Long empId) {
    assign(comId, empId, EmployeeRoleAssignmentPolicy.Scenario.FIRST_EMPLOYEE);
  }

  @Transactional
  public void assignForNormalEmployee(Long comId, Long empId) {
    assign(comId, empId, EmployeeRoleAssignmentPolicy.Scenario.NORMAL_EMPLOYEE);
  }

  private void assign(Long comId, Long empId, EmployeeRoleAssignmentPolicy.Scenario scenario) {
    // 1) 기본 역할 존재 보장
    defaultRoleCommandService.ensureDefaults(comId);

    // 2) 정책이 정한 roleKey 목록
    List<String> keys = employeeRoleAssignmentPolicy.roleKeysToAssign(scenario);

    // 3) roleKey -> roleId 조회 후 매핑 저장(멱등)
    for (String key : keys) {
      Role role = roleRepository.findByComIdAndRoleKey(comId, key)
          .orElseThrow(() -> new BusinessException(RoleErrorCode.ROLE_NOT_FOUND));
      }
    }
  }

