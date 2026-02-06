package org.hit.hradar.domain.department.command.application.service;

import org.hit.hradar.domain.department.command.application.dto.CreateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.dto.UpdateDepartmentRequest;
import org.hit.hradar.domain.department.command.domain.aggregate.Department;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DepartmentCommandServiceTest {

        @InjectMocks
        private DepartmentCommandService departmentCommandService;

        @Mock
        private DepartmentRepository departmentRepository;

        @Mock
        private EmployeeRepository employeeRepository;

        @Test
        @DisplayName("부서 생성 성공")
        void createDepartment_Success() {
                // given
                Long companyId = 1L;
                CreateDepartmentRequest request = new CreateDepartmentRequest("IT Department", null, null, "02-1234");

                given(departmentRepository.existsByDeptNameAndComIdAndIsDeleted("IT Department", companyId, 'N'))
                                .willReturn(false);

                // when
                departmentCommandService.createDepartment(request, companyId);

                // then
                verify(departmentRepository).save(any(Department.class));
        }

        @Test
        @DisplayName("부서 생성 실패 - 중복된 이름")
        void createDepartment_Fail_DuplicateName() {
                // given
                Long companyId = 1L;
                CreateDepartmentRequest request = new CreateDepartmentRequest("Existing Dept", null, null, "02-1234");

                given(departmentRepository.existsByDeptNameAndComIdAndIsDeleted("Existing Dept", companyId, 'N'))
                                .willReturn(true);

                // when & then
                assertThatThrownBy(() -> departmentCommandService.createDepartment(request, companyId))
                                .isInstanceOf(BusinessException.class)
                                .hasMessageContaining("이미 사용중인 부서명입니다.");
        }

        @Test
        @DisplayName("부서 정보 수정 성공")
        void updateDepartment_Success() {
                // given
                Long deptId = 10L;
                Long companyId = 1L;
                UpdateDepartmentRequest request = new UpdateDepartmentRequest("Updated Dept", null, null, "02-5555");

                Department department = Department.builder()
                                .deptId(deptId)
                                .companyId(companyId)
                                .deptName("Old Dept")
                                .build();

                given(departmentRepository.findByDeptIdAndComIdAndIsDeleted(deptId, companyId, 'N'))
                                .willReturn(Optional.of(department));
                given(departmentRepository.existsByDeptNameAndComIdAndIsDeleted("Updated Dept", companyId, 'N'))
                                .willReturn(false);

                // when
                departmentCommandService.updateDepartment(deptId, companyId, request);

                // then
                assertThat(department.getDeptName()).isEqualTo("Updated Dept");
        }

        @Test
        @DisplayName("부서 삭제 성공")
        void deleteDepartment_Success() {
                // given
                Long deptId = 10L;
                Long companyId = 1L;
                Department department = Department.builder()
                                .deptId(deptId)
                                .companyId(companyId)
                                .deptName("Test")
                                .build();

                given(departmentRepository.findByDeptIdAndComIdAndIsDeleted(deptId, companyId, 'N'))
                                .willReturn(Optional.of(department));

                // when
                departmentCommandService.deleteDepartment(deptId, companyId);

                // then
                assertThat(department.getIsDeleted()).isEqualTo('Y');
        }
}
