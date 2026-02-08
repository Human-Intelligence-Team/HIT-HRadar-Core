package org.hit.hradar.domain.attendance.command.infrastructure;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceOvertime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceOvertimeJpaRepository extends JpaRepository<AttendanceOvertime, Long> {
}
