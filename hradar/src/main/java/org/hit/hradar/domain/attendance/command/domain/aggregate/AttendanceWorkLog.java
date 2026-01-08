package org.hit.hradar.domain.attendance.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "ATTENDANCE_WOKR_LOG")
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

  //근무 시작 시각
  @Column(name = "start_time", nullable = false)
  private LocalDateTime startTime;

  //근무 종료 시각
  @Column(name = "end_time", nullable = false)
  private LocalDateTime endTime;

  //로그 근무 시각
  @Column(name = "work_minutes", nullable = false, length = 100)
  private String workMinutes;

  //근무 장소
  @Column(name = "location", nullable = false, length = 100)
  private String location;

  //생성자


  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted;



}
