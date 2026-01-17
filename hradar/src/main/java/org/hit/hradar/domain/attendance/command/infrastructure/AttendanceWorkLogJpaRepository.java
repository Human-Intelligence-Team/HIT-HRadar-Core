package org.hit.hradar.domain.attendance.command.infrastructure;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceWorkLogJpaRepository
    extends JpaRepository<AttendanceWorkLog, Long> {

  boolean existsByAttendanceIdAndEndAtIsNull(Long attendanceId);

  AttendanceWorkLog
  findTopByAttendanceIdAndEndAtIsNullOrderByStartAtDesc(Long attendanceId);
}