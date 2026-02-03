package org.hit.hradar.domain.attendance.command.infrastructure;

import java.time.LocalDateTime;
import java.util.Optional;
import org.hit.hradar.domain.attendance.command.domain.aggregate.ApprovalStatus;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceWorkPlanJpaRepository extends
    JpaRepository<AttendanceWorkPlan, Long> {


  Optional<AttendanceWorkPlan> findByDocId(Long docId);

  Optional<AttendanceWorkPlan> findByEmpIdAndStatusAndStartAtLessThanEqualAndEndAtGreaterThanEqual(
      Long empId,
      ApprovalStatus status,
      LocalDateTime now1,
      LocalDateTime now2
  );
}
