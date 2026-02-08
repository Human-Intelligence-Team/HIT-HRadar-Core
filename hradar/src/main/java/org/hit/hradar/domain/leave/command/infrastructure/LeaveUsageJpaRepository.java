package org.hit.hradar.domain.leave.command.infrastructure;


import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveUsageJpaRepository extends JpaRepository<LeaveUsage, Long>  {

  void deleteByLeaveId(Long leaveId);

  boolean existsByLeaveId(Long leaveId);
}
