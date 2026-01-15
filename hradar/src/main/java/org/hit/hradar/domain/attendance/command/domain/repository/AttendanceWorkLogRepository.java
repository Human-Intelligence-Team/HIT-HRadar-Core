package org.hit.hradar.domain.attendance.command.domain.repository;

import java.util.List;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;

public interface AttendanceWorkLogRepository {

  AttendanceWorkLog save(AttendanceWorkLog log);

  // 특정 근태의 전체 로그 (상세 타임라인)
  List<AttendanceWorkLog> findByAttendanceIdOrderByStartAtAsc(
      Long attendanceId
  );

}
