package org.hit.hradar.domain.attendance.command.infrastructure;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceWorkLogRepository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AttendanceWorkLogRepositoryAdapter
  implements AttendanceWorkLogRepository {

    private final AttendanceWorkLogJpaRepository jpaRepository;

    @Override
    public AttendanceWorkLog save(AttendanceWorkLog log) {
      return jpaRepository.save(log);
    }

    // === 정정 승인용 ===
    @Override
    public void updateWorkLogTime(Long workLogId, String requestedValue) {
      AttendanceWorkLog log = jpaRepository.findById(workLogId).orElseThrow();
      // requestedValue 예: "2026-01-18T09:00~2026-01-18T18:00"
      String[] times = requestedValue.split("~");
      LocalDateTime newStartAt = LocalDateTime.parse(times[0]);
      LocalDateTime newEndAt = LocalDateTime.parse(times[1]);

      log.changeTime(newStartAt, newEndAt);
    }

    @Override
    public void updateWorkLogLocation(Long workLogId, String requestedValue) {
      AttendanceWorkLog log = jpaRepository.findById(workLogId).orElseThrow();
      log.changeLocation(requestedValue);
    }

}
