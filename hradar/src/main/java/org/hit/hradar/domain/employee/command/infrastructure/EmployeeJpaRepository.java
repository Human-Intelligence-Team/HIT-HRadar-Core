package org.hit.hradar.domain.employee.command.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository
    extends JpaRepository<Employee, Long>, EmployeeRepository {

  @Override
  Optional<Employee> findByEmpIdAndComIdAndIsDeleted(Long empId, Long comId, char isDeleted);

  @Override
  boolean existsByEmployeeNoAndComIdAndIsDeleted(String employeeNo, Long comId, char isDeleted);

  @Override
  boolean existsByEmailAndComIdAndIsDeleted(String email, Long comId, char isDeleted);
}
