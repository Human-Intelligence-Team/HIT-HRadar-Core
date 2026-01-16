package org.hit.hradar.domain.attendance.command.domain.repository;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;

public interface AttendanceWorkLogRepository {

  AttendanceWorkLog save(AttendanceWorkLog log);


}