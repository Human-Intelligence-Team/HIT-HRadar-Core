package org.hit.hradar.domain.attendance.command.domain.repository;

import org.hit.hradar.domain.attendance.command.domain.aggregate.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

  
}
