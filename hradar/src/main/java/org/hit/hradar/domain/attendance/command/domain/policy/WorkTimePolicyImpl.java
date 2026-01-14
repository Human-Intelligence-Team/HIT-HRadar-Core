package org.hit.hradar.domain.attendance.command.domain.policy;

//22시 강제 퇴근 정책

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkLogType;
import org.hit.hradar.domain.attendance.command.domain.repository.AttendanceWorkLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkTimePolicyImpl implements WorkTimePolicy{

  private static final LocalTime CUTOFF_TIME = LocalTime.of(22, 0);

  private final AttendanceWorkLogRepository attendanceWorkLogRepository;

  @Override
  public void enforce(LocalDateTime now) {

    //오늘 날짜 기준 22시 이전이면 아무것도 하지 않음
    if (now.toLocalTime().isBefore(CUTOFF_TIME)) {
      return;
    }

    //오늘 날짜 + 아직 종료되지 않은 정규근무 로그 조회
    LocalDate today = now.toLocalDate();

    //logType = REGULAR, endAt IS NULL, startAt 날짜 = 오늘
    List<AttendanceWorkLog> openLogs =
        attendanceWorkLogRepository.findOpenRegularLogs(
            today,
            WorkLogType.REGULAR
        );

    // 3. 전부 22:00으로 강제 종료
    LocalDateTime forcedEndAt =
        LocalDateTime.of(today, CUTOFF_TIME);

    for (AttendanceWorkLog log : openLogs) {
      log.close(forcedEndAt);
    }
  }
}
