package org.hit.hradar.domain.attendance.query.service;

import java.time.LocalDateTime;
import java.util.Optional;
import org.hit.hradar.domain.attendance.query.dto.WorkPlanDto;

public interface WorkPlanQueryService {

  Optional<WorkPlanDto> findCurrentWorkPlan(
  Long empId,
  LocalDateTime now
  );
}
