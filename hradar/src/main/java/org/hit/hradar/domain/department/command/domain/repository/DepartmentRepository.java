package org.hit.hradar.domain.department.command.domain.repository;

import org.hit.hradar.domain.department.command.domain.aggregate.Department;

import java.util.Optional;

public interface DepartmentRepository {
    Optional<Department> findById(Long deptId);
}
