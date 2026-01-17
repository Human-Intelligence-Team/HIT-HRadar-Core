package org.hit.hradar.domain.attendance.command.domain.repository;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;

public interface AttendanceWorkLogRepository {

  AttendanceWorkLog save(AttendanceWorkLog log);

  //츨퇴근 로그 도메인 레포
  //출/퇴근 판단의 기준 WorkLog

  // 근무 로그 시간 변경 (정정 승인용)
  void updateWorkLogTime(Long workLogId, String requestedValue);

  // 근무 장소/유형 변경 (정정 승인용)
  void updateWorkLogLocation(Long workLogId, String requestedValue);
}