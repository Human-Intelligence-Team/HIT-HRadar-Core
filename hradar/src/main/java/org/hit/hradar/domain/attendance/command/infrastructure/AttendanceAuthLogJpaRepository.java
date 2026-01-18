package org.hit.hradar.domain.attendance.command.infrastructure;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceAuthLog;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceAuthLogRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceAuthLogJpaRepository extends JpaRepository<AttendanceAuthLog, Long> {

}
