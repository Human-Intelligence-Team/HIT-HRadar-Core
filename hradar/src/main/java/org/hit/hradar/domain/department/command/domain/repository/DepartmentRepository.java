package org.hit.hradar.domain.department.command.domain.repository;

import org.hit.hradar.domain.department.command.domain.aggregate.Department;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepository {

  Optional<Department> findById(Long deptId);

    Department save(Department department);

    Optional<Department> findByDeptIdAndCompanyIdAndIsDeleted(Long deptId, Long companyId, char isDeleted);

    List<Department> findAllByCompanyIdAndIsDeleted(Long companyId, char isDeleted);

    boolean existsByDeptNameAndCompanyIdAndIsDeleted(String deptName, Long companyId, char isDeleted);


}