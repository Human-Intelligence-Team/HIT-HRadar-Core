package org.hit.hradar.domain.employee.command.domain.repository;

import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {


}