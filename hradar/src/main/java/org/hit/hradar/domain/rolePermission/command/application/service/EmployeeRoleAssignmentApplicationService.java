package org.hit.hradar.domain.rolePermission.command.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.RoleErrorCode;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.EmployeeRole;
import org.hit.hradar.domain.rolePermission.command.domain.policy.EmployeeRoleAssignmentPolicy;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleEmpJpaRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeRoleAssignmentApplicationService {

  private final EmployeeRoleAssignmentPolicy employeeRoleAssignmentPolicy;
  private final DefaultRoleCommandService defaultRoleCommandService;
  private final RoleJpaRepository roleRepository;
  private final RoleEmpJpaRepository roleEmpRepository;
  private final org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository employeeRepository;

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

    // 3) roleKey -> roleId 조회 후 매핑 저장
    for (String key : keys) {
      Role role = roleRepository.findByComIdAndRoleKey(comId, key)
          .orElseThrow(() -> new BusinessException(RoleErrorCode.ROLE_NOT_FOUND));

      if (!roleEmpRepository.existsByEmpIdAndRoleId(empId, role.getRoleId())) {
        roleEmpRepository.save(
            EmployeeRole.builder()
                .empId(empId)
                .roleId(role.getRoleId())
                .build());
      }
    }
  }

  @Transactional
  public void updateEmployeeRoles(Long comId, Long empId, List<Long> roleIds) {
    // 사원 존재 및 회사 확인
    employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N')
        .orElseThrow(() -> new BusinessException(org.hit.hradar.domain.employee.EmployeeErrorCode.EMPLOYEE_ERROR_CODE));

    // 기존 권한 삭제
    roleEmpRepository.deleteByEmpId(empId);

    // 새 권한 부여

    // 0) 'EMPLOYEE' 역할(기본 사원 권한)은 필수이므로 강제로 추가
    // (프론트에서 빠뜨려도 백엔드에서 채워줌)
    Role employeeRole = roleRepository.findByComIdAndRoleKey(comId, "EMPLOYEE")
        .orElseThrow(() -> new BusinessException(RoleErrorCode.ROLE_NOT_FOUND));

    List<Long> finalRoleIds = new java.util.ArrayList<>(roleIds);
    if (!finalRoleIds.contains(employeeRole.getRoleId())) {
      finalRoleIds.add(employeeRole.getRoleId());
    }

    // 중복 제거
    List<Long> distinctRoleIds = finalRoleIds.stream().distinct().toList();
    for (Long roleId : distinctRoleIds) {
      Role role = roleRepository.findById(roleId)
          .orElseThrow(() -> new BusinessException(RoleErrorCode.ROLE_NOT_FOUND));

      // Role의 CompanyId 체크 (시스템 역할 or 같은 회사 커스텀 역할)
      if (role.getIsSystem() == 'N' && !role.getComId().equals(comId)) {
        throw new BusinessException(RoleErrorCode.ROLE_NOT_FOUND);
      }

      roleEmpRepository.save(
          EmployeeRole.builder()
              .empId(empId)
              .roleId(roleId)
              .build());
    }
  }
}
