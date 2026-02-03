package org.hit.hradar.domain.attendance.command.domain.aggregate;

public enum AttendanceStatus {

  NORMAL,     // 정상 근무
  LEAVE,
  LATE,       // 지각
  EARLY_LEAVE,// 조퇴
  ABSENT      // 결근
}
