package org.hit.hradar.domain.employee.command.domain.repository;

import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findById(Long empId);

  Employee save(Employee employee);

  Optional<Employee> findByEmpIdAndComIdAndIsDeleted(Long empId, Long comId, char isDeleted);

  boolean existsByEmployeeNoAndComIdAndIsDeleted(String employeeNo, Long comId, char isDeleted);

  boolean existsByEmailAndComIdAndIsDeleted(String email, Long comId, char isDeleted);
}