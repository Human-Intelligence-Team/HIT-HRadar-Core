package org.hit.hradar.domain.employee.command.domain.repository;

import java.util.Optional;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;

public interface EmployeeRepository {
  Optional<Employee> findById(Long empId);

  Employee save(Employee employee);

  Optional<Employee> findByEmpIdAndComIdAndIsDeleted(Long empId, Long companyId, char isDeleted);

  boolean existsByEmployeeNoAndComIdAndIsDeleted(String employeeNo, Long companyId, char isDeleted);

  boolean existsByEmailAndComIdAndIsDeleted(String email, Long companyId, char isDeleted);
}