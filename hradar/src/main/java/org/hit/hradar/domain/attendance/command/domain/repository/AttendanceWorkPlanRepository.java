package org.hit.hradar.domain.attendance.command.domain.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceApprovalStatus;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkPlan;

public interface AttendanceWorkPlanRepository {

  Optional<AttendanceWorkPlan> findByDocId(Long docId);

  AttendanceWorkPlan save(AttendanceWorkPlan plan);

  Optional<AttendanceWorkPlan> findByEmpIdAndStatusAndStartAtBetween(
      Long empId,
      AttendanceApprovalStatus status,
      LocalDateTime startAt,
      LocalDateTime endAt
  );
}

