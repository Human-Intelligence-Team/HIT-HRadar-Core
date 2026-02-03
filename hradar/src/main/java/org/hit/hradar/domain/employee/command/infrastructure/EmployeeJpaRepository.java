package org.hit.hradar.domain.employee.command.infrastructure;

import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository
    extends JpaRepository<Employee, Long>, EmployeeRepository {

}