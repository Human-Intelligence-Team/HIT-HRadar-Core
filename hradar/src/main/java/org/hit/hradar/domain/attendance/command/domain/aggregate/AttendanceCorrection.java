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
@Table(name = "attendance_correction")
@Getter
public class AttendanceCorrection extends BaseTimeEntity {

  //근태 정정id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attendance_correction")
  private Long attendanceCorrection;

  //근태id
  @Column(name = "attendance_id", nullable = false)
  private Long attendanceId;

  //근무장소 로그id
  @Column(name ="wokr_log_id", nullable = false)
  private Long workLogId;

  //결정자 사원id
  @Column(name = "decider_id", nullable = false)
  private Long deciderId;

  //요청자 사원id
  @Column(name = "requester_id", nullable = false)
  private Long requesterId;

  //정정 유형
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "type", nullable = false)
  private CorrectionType Type = CorrectionType.TIME_CHANGE;

  //정정 사유
  @Column(name = "reason", nullable = false, length = 255)
  private String reason;

  //변경하려는 값(저장시)
  @Column(name = "requested_value", nullable = false, length = 255)
  private String requestedValue;

  //신청 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private CorrectionStatus status = CorrectionStatus.REQUESTED;

  //요청 일자(같은 요청이 여러 개일 경우)
  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  //결정 일자
  @Column(name = "decided_at")
  private LocalDateTime decidedAt;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}
