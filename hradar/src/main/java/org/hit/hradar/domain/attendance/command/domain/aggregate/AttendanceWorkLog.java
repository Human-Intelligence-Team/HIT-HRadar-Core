package org.hit.hradar.domain.attendance.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.Duration;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "attendance_work_log")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendanceWorkLog extends BaseTimeEntity {

  //근무장소 로그ID
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "work_log_id")
  private Long workLogId;

  //근무 로그 유형(출근/퇴근)
  @Enumerated(EnumType.STRING)
  @Column(name = "work_log_type", nullable = false)
  private WorkLogType workLogType = WorkLogType.CHECK_IN;

  //근태ID
  @Column(name = "attendance_id", nullable = false)
  private Long attendanceId;

  //근무 시작 시각
  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  //근무 종료 시각
  @Column(name = "end_at")
  private LocalDateTime endAt;

  //퇴근 시 계산됨 (출근 시 NULL)
  @Column(name = "worked_minutes")
  private Integer workedMinutes;

  //근무 장소
  @Column(name = "location", nullable = false, length = 100)
  private String location;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  //출근용 생성자
  public AttendanceWorkLog(
      Long attendanceId,
      WorkLogType workLogType,
      LocalDateTime startAt,
      String location
  ) {
    this.attendanceId = attendanceId;
    this.workLogType = workLogType;
    this.startAt = startAt;
    this.location = location;
  }

  //퇴근용 생성자
  public void close(LocalDateTime endAt) {
    this.endAt = endAt;
    this.workLogType = WorkLogType.CHECK_OUT;
    this.workedMinutes =
        (int) Duration.between(this.startAt, endAt).toMinutes();
  }

  // 시간 정정
  public void changeTime(LocalDateTime newStartAt, LocalDateTime newEndAt) {
    this.startAt = newStartAt;
    this.endAt = newEndAt;
  }

  // 장소 정정
  public void changeLocation(String newLocation) {
    this.location = newLocation;
  }
}
