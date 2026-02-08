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

      // 순환 참조 방지 (부모로 지정하려는 부서가 현재 부서의 하위 부서인지 체크)
      // 간단하게는 재귀적으로 부모를 타고 올라가서 deptId를 만나는지 확인
      // 여기서는 구현 복잡도를 낮추기 위해, "부모가 나를 자식으로 가지고 있는가?"보다는
      // "내가 부모의 조상인가?"를 확인해야 함.
      validateCircularDependency(deptId, request.getParentDeptId(), companyId);
    }

    if (request.getManagerEmpId() != null) {
      employeeRepository.findByEmpIdAndComIdAndIsDeleted(request.getManagerEmpId(), companyId, 'N')
          .orElseThrow(() -> new BusinessException(EmployeeErrorCode.EMPLOYEE_ERROR_CODE));
    }

    department.updateDepartment(request.getDeptName(), request.getParentDeptId(), request.getManagerEmpId(),
        request.getDeptPhone());
  }

  @Transactional
  public void deleteDepartment(Long deptId, Long companyId) {
    Department department = departmentRepository.findByDeptIdAndComIdAndIsDeleted(deptId, companyId, 'N')
        .orElseThrow(() -> new BusinessException(DepartmentErrorCode.DEPARTMENT_NOT_FOUND));

    // 하위 부서 존재 여부 체크
    if (departmentRepository.existsByParentDeptIdAndIsDeleted(deptId, 'N')) {
      throw new BusinessException(DepartmentErrorCode.CANNOT_DELETE_HAS_CHILDREN);
    }

    // 소속 사원 존재 여부 체크
    if (employeeRepository.existsByDeptIdAndIsDeleted(deptId, 'N')) {
      throw new BusinessException(DepartmentErrorCode.CANNOT_DELETE_HAS_EMPLOYEES);
    }

    department.isDeleted();
  }

  // 순환 참조 검증 로직
  private void validateCircularDependency(Long currentDeptId, Long targetParentId, Long companyId) {
    Long nextParentId = targetParentId;
    while (nextParentId != null) {
      if (nextParentId.equals(currentDeptId)) {
        throw new BusinessException(DepartmentErrorCode.INVALID_PARENT_DEPARTMENT); // 순환 참조 발생
      }
      Department parent = departmentRepository.findByDeptIdAndComIdAndIsDeleted(nextParentId, companyId, 'N')
          .orElse(null);
      if (parent == null)
        break;
      nextParentId = parent.getParentDeptId();
    }
  }
}
