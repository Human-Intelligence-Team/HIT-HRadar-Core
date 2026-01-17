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
  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  //근무 장소
  @Column(name = "location", nullable = false, length = 100)
  private String location;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}
