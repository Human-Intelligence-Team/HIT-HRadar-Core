package org.hit.hradar.domain.department.command.infrastructure;

import org.hit.hradar.domain.department.command.domain.aggregate.Department;
import org.hit.hradar.domain.department.command.domain.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentJpaRepository
        extends JpaRepository<Department, Long>, DepartmentRepository {

  boolean existsByDeptIdAndComIdAndIsDeleted(Long deptId, Long comId, Character isDeleted);

}
