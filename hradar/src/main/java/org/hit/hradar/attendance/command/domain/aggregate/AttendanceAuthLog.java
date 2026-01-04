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
@Table(name = "ATTENDANCE_AUTH_LOG")
@Getter
public class AttendanceAuthLog {

  //인증로그 id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_log_id")
  private Integer authLogId;

  //인증 여부
  @Enumerated(EnumType.ORDINAL)
  @Column(name = "auth_result", nullable = false)
  private AuthResult authResult;

  //인증 시각
  @Column(name = "auth_at", nullable = false)
  private LocalDateTime authAt;

  //근태 id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "attendance_id", nullable = false)
  private Attendance attendanceId;

  //접속ip주소
  @Column(name = "ip_address", nullable = false)
  private String ipAddress;

}
