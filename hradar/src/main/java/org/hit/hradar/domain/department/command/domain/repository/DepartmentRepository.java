package org.hit.hradar.domain.department.command.domain.repository;

import java.util.List;
import org.hit.hradar.domain.department.command.domain.aggregate.Department;

import java.util.Optional;

public interface DepartmentRepository {
  Optional<Department> findById(Long deptId);

  Department save(Department department);

  Optional<Department> findByDeptIdAndComIdAndIsDeleted(Long deptId, Long comId, char isDeleted);

  List<Department> findAllByComIdAndIsDeleted(Long comId, char isDeleted);

  boolean existsByDeptIdAndComIdAndIsDeleted(Long deptId, Long comId, Character isDeleted);

  boolean existsByDeptNameAndComIdAndIsDeleted(String deptName, Long comId, char isDeleted);
}