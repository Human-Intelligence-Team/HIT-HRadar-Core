package org.hit.hradar.domain.leave.command.infrastructure;

import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveGrant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveGrantJpaRepository extends JpaRepository<LeaveGrant, Long> {
}
