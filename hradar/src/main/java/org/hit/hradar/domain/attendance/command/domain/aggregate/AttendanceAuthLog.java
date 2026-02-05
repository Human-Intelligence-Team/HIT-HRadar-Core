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
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "attendance_auth_log")
@Getter
@NoArgsConstructor
public class AttendanceAuthLog extends BaseTimeEntity {

  // 인증 로그 ID
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_log_id")
  private Long authLogId;

  // 근태 ID
  @Column(name = "attendance_id", nullable = false)
  private Long attendanceId;

  // 인증 수단 (IP 고정)
  @Column(name = "auth_type", nullable = false, length = 50)
  private String authType;

  // 인증 결과
  @Enumerated(EnumType.STRING)
  @Column(name = "auth_result", nullable = false)
  private AuthResult authResult;

  // 접속 IP
  @Column(name = "ip_address", nullable = false, length = 45)
  private String ipAddress;

  // MAC 주소 (선택)
  @Column(name = "mac_address", length = 50)
  private String macAddress;

  // 삭제 여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  // 실제 행위 시각 (DB 제약 조건 대응)
  @Column(name = "acted_at", nullable = false, updatable = false)
  private LocalDateTime actedAt;

  // ===== 생성자 =====
  public AttendanceAuthLog(Long attendanceId, String ipAddress) {
    this.attendanceId = attendanceId;
    this.ipAddress = ipAddress;
    this.authType = "IP";
    this.authResult = AuthResult.SUCCESS;
    this.isDeleted = 'N';
    this.actedAt = LocalDateTime.now();
  }
}
