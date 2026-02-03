package org.hit.hradar.domain.leave.command.domain.repository;

import org.hit.hradar.domain.leave.command.domain.aggregate.LeaveUsage;

public interface LeaveUsageRepository {

  LeaveUsage save(LeaveUsage usage);

  void applyUsage(Long docId);
}
