package org.hit.hradar.domain.attendance.command.domain.infrastructure;

import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceJpaRepository
    extends AttendanceRepository, JpaRepository<Attendance, Long> {
}
