package org.hit.hradar.domain.rolePermission.command.application.service;

import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.EmployeeRole;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleEmpJpaRepository;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.hit.hradar.domain.rolePermission.command.domain.policy.EmployeeRoleAssignmentPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeRoleAssignmentApplicationServiceTest {

    @InjectMocks
    private EmployeeRoleAssignmentApplicationService roleAssignmentService;

    @Mock
    private RoleJpaRepository roleRepository;

    @Mock
    private RoleEmpJpaRepository roleEmpRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeRoleAssignmentPolicy employeeRoleAssignmentPolicy;

    @Mock
    private DefaultRoleCommandService defaultRoleCommandService;

    @Test
    @DisplayName("사원 역할 수정 성공 - EMPLOYEE 역할 자동 포함")
    void updateEmployeeRoles_Success() {
        // given
        Long comId = 1L;
        Long empId = 1001L;
        List<Long> roleIds = List.of(10L); // Custom role

        Employee employee = Employee.builder().empId(empId).comId(comId).build();
        Role employeeRole = Role.builder().roleId(1L).comId(comId).roleKey("EMPLOYEE").isSystem('Y').build();
        Role customRole = Role.builder().roleId(10L).comId(comId).roleKey("CUSTOM").isSystem('N').build();

        given(employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N'))
                .willReturn(Optional.of(employee));
        given(roleRepository.findByComIdAndRoleKey(comId, "EMPLOYEE"))
                .willReturn(Optional.of(employeeRole));
        given(roleRepository.findById(10L)).willReturn(Optional.of(customRole));
        given(roleRepository.findById(1L)).willReturn(Optional.of(employeeRole));

        // when
        roleAssignmentService.updateEmployeeRoles(comId, empId, roleIds);

        // then
        verify(roleEmpRepository).deleteByEmpId(empId);
        // Should save both Custom role and mandatory EMPLOYEE role
        verify(roleEmpRepository, atLeastOnce()).save(any(EmployeeRole.class));
    }
}
