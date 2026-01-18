package org.hit.hradar.domain.attendance.command.infrastructure;

import java.util.Optional;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceWorkLogJpaRepository
    extends JpaRepository<AttendanceWorkLog, Long> {

  boolean existsByAttendanceIdAndEndAtIsNull(Long attendanceId);

  Optional<AttendanceWorkLog>
  findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(Long attendanceId);
}