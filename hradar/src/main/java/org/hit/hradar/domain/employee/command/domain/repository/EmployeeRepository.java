package org.hit.hradar.domain.employee.command.domain.repository;

import java.util.List;
import java.util.Optional;
import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;

public interface EmployeeRepository {

  Optional<Employee> findById(Long empId);

  Employee save(Employee employee);

  Optional<Employee> findByEmpIdAndComIdAndIsDeleted(Long empId, Long companyId, Character isDeleted);

  boolean existsByEmployeeNoAndComIdAndIsDeleted(String employeeNo, Long companyId, Character isDeleted);

  boolean existsByEmailAndComIdAndIsDeleted(String email, Long companyId, Character isDeleted);

  List<Employee> findAllByComIdAndIsDeleted(Long companyId, Character isDeleted);

  List<Employee> findAllByComIdAndDeptIdAndIsDeleted(Long companyId, Long deptId, Character isDeleted);

  List<Employee> findAllByComIdAndPositionIdAndIsDeleted(Long companyId, Long positionId, Character isDeleted);

  boolean existsByDeptIdAndIsDeleted(Long deptId, Character isDeleted);

  boolean existsByPositionIdAndIsDeleted(Long positionId, Character isDeleted);
}
