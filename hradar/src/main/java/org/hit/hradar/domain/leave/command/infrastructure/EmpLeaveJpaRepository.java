package org.hit.hradar.domain.leave.command.infrastructure;

import org.hit.hradar.domain.leave.command.domain.repository.EmpLeaveRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpLeaveJpaRepository extends JpaRepository<EmpLeaveRepository, Long>  {

}
