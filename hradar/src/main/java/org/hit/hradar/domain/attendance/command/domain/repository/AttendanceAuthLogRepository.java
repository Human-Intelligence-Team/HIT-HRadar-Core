package org.hit.hradar.domain.attendance.command.domain.repository;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceAuthLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceAuthLogRepository extends JpaRepository<AttendanceAuthLog, Long> {

}
