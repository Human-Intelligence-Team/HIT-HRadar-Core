package org.hit.hradar.domain.employee.command.infrastructure;

import org.hit.hradar.domain.employee.command.domain.aggregate.EmployeeMovementHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeMovementHistoryJpaRepository extends JpaRepository<EmployeeMovementHistory, Long> {}
