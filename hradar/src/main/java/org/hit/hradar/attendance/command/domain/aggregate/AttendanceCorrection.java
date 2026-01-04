package org.hit.hradar.attendance.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;

@Entity
@Table(name = "ATTENDANCE_CORRECTION")
@Getter
public class AttendanceCorrection {

  //근태 정정id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "attendance_correction")
  private Long attendanceCorrection;

  //근태id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id", nullable = false)
  private Attendance attendanceId;

  //정정 유형
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "correction_type", nullable = false)
  private CorrectionType correctionType;

  //정정 사유
  @Column(name = "reason", nullable = false)
  private String reason;

  //변경하려는 값(저장시)
  @Column(name = "requested_value", nullable = false)
  private String requestedValue;

  //신청 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private Status status;

  //요청 일자(같은 요청이 여러 개일 경우)
  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  //결정 일자
  @Column(name = "decided_at")
  private LocalDateTime decidedAt;

  //결정자id
  @Column(name = "decided_by")
  private Integer decidedBy;

}
