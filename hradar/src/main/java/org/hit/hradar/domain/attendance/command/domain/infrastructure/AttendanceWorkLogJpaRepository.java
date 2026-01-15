package org.hit.hradar.domain.attendance.command.domain.infrastructure;

import java.time.LocalDateTime;
import java.util.List;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttendanceWorkLogJpaRepository
    extends JpaRepository<AttendanceWorkLog, Long> {

  // 타임라인
  List<AttendanceWorkLog> findByAttendanceIdOrderByStartAtAsc(Long attendanceId);

  // 최초 출근
  @Query("""
      select min(w.startAt)
      from AttendanceWorkLog w
      where w.attendanceId = :attendanceId
        and w.workLogType = 'CHECK_IN'
        and w.isDeleted = 'N'
  """)
  LocalDateTime findFirstCheckInTime(Long attendanceId);

  // 최종 퇴근
  @Query("""
      select max(w.endAt)
      from AttendanceWorkLog w
      where w.attendanceId = :attendanceId
        and w.workLogType = 'CHECK_OUT'
        and w.isDeleted = 'N'
  """)
  LocalDateTime findLastCheckOutTime(Long attendanceId);
}
