package org.hit.hradar.domain.department.command.application.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.department.DepartmentErrorCode;
import org.hit.hradar.domain.department.command.application.dto.CreateDepartmentRequest;
import org.hit.hradar.domain.department.command.application.dto.UpdateDepartmentRequest;
import org.hit.hradar.domain.department.command.domain.aggregate.Department;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.hit.hradar.domain.employee.EmployeeErrorCode;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.hit.hradar.global.exception.BusinessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DepartmentCommandService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;

    @Transactional
    public Long createDepartment(CreateDepartmentRequest request, Long companyId) {
        if (departmentRepository.existsByDeptNameAndComIdAndIsDeleted(request.getDeptName(), companyId, 'N')) {
            throw new BusinessException(DepartmentErrorCode.DUPLICATE_DEPARTMENT_NAME);
        }

      if (request.getParentDeptId() != null) {
        boolean exists = departmentRepository
            .existsByDeptIdAndComIdAndIsDeleted(request.getParentDeptId(), companyId, 'N'); // 존재 여부 쿼리

        if (!exists) {
          throw new BusinessException(DepartmentErrorCode.INVALID_PARENT_DEPARTMENT);
        }
      }

      if (request.getManagerEmpId() != null) {
        employeeRepository.findByEmpIdAndComIdAndIsDeleted(request.getManagerEmpId(), companyId, 'N')
            .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));
      }


      Department department = Department.builder()
                .companyId(companyId)
                .deptName(request.getDeptName())
                .parentDeptId(request.getParentDeptId())
                .managerEmpId(request.getManagerEmpId())
                .deptPhone(request.getDeptPhone())
                .build();
        departmentRepository.save(department);
        return department.getDeptId();
    }

    @Transactional
    public void updateDepartment(Long deptId, Long companyId, UpdateDepartmentRequest request) {
        if (deptId.equals(request.getParentDeptId())) {
            throw new BusinessException(DepartmentErrorCode.INVALID_PARENT_DEPARTMENT);
        }

        Department department = departmentRepository.findByDeptIdAndComIdAndIsDeleted(deptId, companyId, 'N')
                .orElseThrow(() -> new BusinessException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND));

        if (!department.getDeptName().equals(request.getDeptName()) &&
            departmentRepository.existsByDeptNameAndComIdAndIsDeleted(request.getDeptName(), companyId, 'N')) {
            throw new BusinessException(DepartmentErrorCode.DUPLICATE_DEPARTMENT_NAME);
        }

      if (request.getParentDeptId() != null) {
        boolean exists = departmentRepository
            .existsByDeptIdAndComIdAndIsDeleted(request.getParentDeptId(), companyId, 'N');

        if (!exists) {
          throw new BusinessException(DepartmentErrorCode.INVALID_PARENT_DEPARTMENT);
        }

      if (request.getManagerEmpId() != null) {
        employeeRepository.findByEmpIdAndComIdAndIsDeleted(request.getManagerEmpId(), companyId, 'N')
            .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));
      }

        department.updateDepartment(request.getDeptName(), request.getParentDeptId(), request.getManagerEmpId(), request.getDeptPhone());
    }
    }

    @Transactional
    public void deleteDepartment(Long deptId, Long companyId) {
        Department department = departmentRepository.findByDeptIdAndComIdAndIsDeleted(deptId, companyId, 'N')
                .orElseThrow(() -> new BusinessException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND));
        department.isDeleted();
    }
}
