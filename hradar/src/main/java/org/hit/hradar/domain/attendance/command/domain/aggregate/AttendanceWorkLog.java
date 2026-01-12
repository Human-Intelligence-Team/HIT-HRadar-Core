package org.hit.hradar.domain.attendance.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "attendance_work_log")
@Getter
public class AttendanceWorkLog extends BaseTimeEntity {

  //근무장소 로그ID
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "work_log_id")
  private Long workLogId;

  //근태ID
  @Column(name = "attendance_id", nullable = false)
  private Long attendanceId;

  //초과근무(정규/초과)
  @Enumerated(EnumType.STRING)
  @Column(name = "work_log_type", nullable = false)
  private WorkLogType logType = WorkLogType.REGULAR;

  //근무 시작 시각
  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  //근무 종료 시각
  @Column(name = "end_at")
  private LocalDateTime endAt;

  //로그 근로 시각
  @Column(name = "worked_minutes", nullable = false, length = 100)
  private String workedMinutes;

  //근무 장소
  @Column(name = "location", nullable = false, length = 100)
  private String location;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  //출근(정규근무) 로그 생성
  public static AttendanceWorkLog createCheckIn(
      Long attendanceId,
      LocalDateTime startAt,
      String location
  ) {
    AttendanceWorkLog log = new AttendanceWorkLog();
    log.attendanceId = attendanceId;
    log.startAt = startAt;
    log.endAt = null;
    log.location = location;
    log.logType = WorkLogType.REGULAR;
    log.isDeleted = 'N';
    return log;
  }

  //퇴근 처리
  public void close(LocalDateTime endAt) {
    this.endAt = endAt;
  }
}
