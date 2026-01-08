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
@Table(name = "ATTENDANCE_CORRECTION")
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
  @Column(name = "decided_by", nullable = false)
  private Long decidedBy;

  //요청자 사원id
  @Column(name = "requested_by", nullable = false)
  private Long requestedBy;

  //정정 유형
  @Column(name = "correction_type", nullable = false,length = 50)
  private String correctionType;

  //정정 사유
  @Column(name = "reason", nullable = false, length = 255)
  private String reason;

  //변경하려는 값(저장시)
  @Column(name = "requested_value", nullable = false, length = 255)
  private String requestedValue;

  //신청 상태
  @Column(name = "status", nullable = false, length = 50)
  private String status;

  //요청 일자(같은 요청이 여러 개일 경우)
  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  //결정 일자
  @Column(name = "decided_at")
  private LocalDateTime decidedAt;

  //생성자

  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted;

}
