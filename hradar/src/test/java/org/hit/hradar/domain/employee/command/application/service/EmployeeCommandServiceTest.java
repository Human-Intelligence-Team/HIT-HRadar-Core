package org.hit.hradar.domain.employee.command.application.service;

import org.hit.hradar.domain.employee.command.application.dto.reponse.UpdateEmployeeProfileResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.CreateFirstEmpRequest;
import org.hit.hradar.domain.employee.command.application.dto.reponse.CreateFirstEmpResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.UpdateEmployeeProfileRequest;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.rolePermission.command.application.service.EmployeeRoleAssignmentApplicationService;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeCommandServiceTest {

        @InjectMocks
        private EmployeeCommandService employeeCommandService;

        @Mock
        private EmployeeRepository employeeRepository;

        @Mock
        private AccountRepository accountRepository;

        @Mock
        private EmployeeRoleAssignmentApplicationService roleAssignmentService;

        @Test
        @DisplayName("첫 번째 사원(CEO) 생성 성공")
        void createFirstEmployee_Success() {
                // given
                CreateFirstEmpRequest req = new CreateFirstEmpRequest(1L, "CEO", "ceo@company.com");

                Employee savedEmp = Employee.builder()
                                .empId(1001L)
                                .comId(1L)
                                .name("CEO")
                                .build();

                given(employeeRepository.save(any(Employee.class))).willReturn(savedEmp);

                // when
                CreateFirstEmpResponse response = employeeCommandService.createFirstEmployee(req);

                // then
                assertThat(response.getEmpId()).isEqualTo(1001L);
                verify(roleAssignmentService).assignForFirstEmployee(1L, 1001L);
        }

        @Test
        @DisplayName("사원 프로필 수정 성공")
        void updateProfile_Success() {
                // given
                Long comId = 1L;
                Long empId = 1001L;
                UpdateEmployeeProfileRequest req = UpdateEmployeeProfileRequest.builder()
                                .name("Updated Name")
                                .email("new@example.com")
                                .build();

                Employee emp = Employee.builder()
                                .empId(empId)
                                .comId(comId)
                                .name("Old Name")
                                .email("old@example.com")
                                .build();

                given(employeeRepository.findByEmpIdAndComIdAndIsDeleted(empId, comId, 'N'))
                                .willReturn(Optional.of(emp));
                given(employeeRepository.existsByEmailAndComIdAndIsDeleted("new@example.com", comId, 'N'))
                                .willReturn(false);

                // when
                UpdateEmployeeProfileResponse response = employeeCommandService.updateProfile(comId, empId, req);

                // then
                assertThat(response.getName()).isEqualTo("Updated Name");
                assertThat(emp.getEmail()).isEqualTo("new@example.com");
        }
}
