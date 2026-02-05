package org.hit.hradar.domain.employee.command.application.service;

import org.hit.hradar.domain.company.command.domain.aggregate.Company;
import org.hit.hradar.domain.company.command.domain.repository.CompanyRepository;
import org.hit.hradar.domain.employee.command.application.dto.reponse.CreateEmployeeWithAccountResponse;
import org.hit.hradar.domain.employee.command.application.dto.request.CreateEmployeeWithAccountRequest;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.domain.rolePermission.command.application.service.EmployeeRoleAssignmentApplicationService;
import org.hit.hradar.domain.user.command.domain.aggregate.Account;
import org.hit.hradar.domain.user.command.domain.repository.AccountRepository;
import org.hit.hradar.domain.user.command.infrastructure.AccountJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmployeeAccountCommandServiceTest {

        @InjectMocks
        private EmployeeAccountCommandService employeeAccountCommandService;

        @Mock
        private EmployeeRepository employeeRepository;

        @Mock
        private AccountRepository accountRepository;

        @Mock
        private AccountJpaRepository userJpaRepository;

        @Mock
        private CompanyRepository companyRepository;

        @Mock
        private PasswordEncoder passwordEncoder;

        @Mock
        private EmployeeRoleAssignmentApplicationService roleAssignmentService;

        @org.junit.jupiter.api.BeforeEach
        void setUp() {
                employeeAccountCommandService = new EmployeeAccountCommandService(
                                employeeRepository,
                                accountRepository,
                                userJpaRepository,
                                companyRepository,
                                passwordEncoder,
                                roleAssignmentService);
        }

        @Test
        @DisplayName("사원 및 계정 생성 성공")
        void createEmployeeWithAccount_Success() {
                // given
                Long comId = 1L;
                CreateEmployeeWithAccountRequest request = CreateEmployeeWithAccountRequest.builder()
                                .employeeNo("EMP001")
                                .loginId("user01")
                                .name("User One")
                                .password("password123")
                                .email("user01@test.com")
                                .build();

                Company company = Company.builder().companyId(comId).comCode("COM123").build();
                Employee savedEmp = Employee.builder().empId(1001L).employeeNo("EMP001").build();
                Account savedAccount = Account.builder().accId(50L).loginId("user01").build();

                given(companyRepository.findByCompanyIdAndIsDeleted(comId, 'N')).willReturn(Optional.of(company));
                given(employeeRepository.existsByEmployeeNoAndComIdAndIsDeleted(anyString(), any(),
                                any(Character.class)))
                                .willReturn(false);
                given(userJpaRepository.existsByComIdAndLoginIdAndStatus(any(), anyString(), any())).willReturn(false);
                given(userJpaRepository.existsByComIdAndEmailAndStatus(any(), anyString(), any())).willReturn(false);
                given(employeeRepository.save(any(Employee.class))).willReturn(savedEmp);
                given(passwordEncoder.encode("password123")).willReturn("hashed_pw");
                given(accountRepository.save(any(Account.class))).willReturn(savedAccount);

                // when
                CreateEmployeeWithAccountResponse response = employeeAccountCommandService
                                .createEmployeeWithAccount(comId, request);

                // then
                assertThat(response.getEmpId()).isEqualTo(1001L);
                assertThat(response.getLoginId()).isEqualTo("user01");
                verify(roleAssignmentService).assignForNormalEmployee(comId, 1001L);
        }
}
