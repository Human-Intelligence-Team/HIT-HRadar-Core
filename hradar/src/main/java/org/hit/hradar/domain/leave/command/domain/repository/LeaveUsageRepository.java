package org.hit.hradar.domain.leave.command.domain.repository;

import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveUsage;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveUsageRepository {

  LeaveUsage save(LeaveUsage usage);

}
