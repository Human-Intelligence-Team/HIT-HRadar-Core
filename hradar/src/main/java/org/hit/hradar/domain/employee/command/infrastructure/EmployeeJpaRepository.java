package org.hit.hradar.domain.employee.command.infrastructure;

import org.hit.hradar.domain.employee.command.domain.aggregate.Employee;
import org.hit.hradar.domain.employee.command.domain.repository.EmployeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long>, EmployeeRepository {

    @Modifying
    @Query("UPDATE Employee e SET e.isDeleted = 'Y', e.employmentType = 'RESIGNED' WHERE e.comId = :comId AND e.isDeleted = 'N'")
    void deactivateEmployeesByComId(@Param("comId") Long comId);

}