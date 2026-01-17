package org.hit.hradar.domain.attendance.command.domain.repository;

import org.hit.hradar.domain.attendance.command.domain.aggregate.AttendanceWorkLog;
import org.hit.hradar.domain.attendance.command.domain.aggregate.WorkType;

public interface AttendanceWorkLogRepository {

  AttendanceWorkLog save(AttendanceWorkLog log);

  //츨퇴근 로그 도메인 레포
  //출/퇴근 판단의 기준 WorkLog

  //열린 WorkLog 존재 여부(end_at Is NULL)
  boolean existsOpenedLog(Long attendanceId);

  //출근처리(CHECK_IN 로그 생성)
  void createCheckIn(Long attendanceId, WorkType workType);

  //퇴근처리
  void closeLastCheckOut(Long attendanceId);

}