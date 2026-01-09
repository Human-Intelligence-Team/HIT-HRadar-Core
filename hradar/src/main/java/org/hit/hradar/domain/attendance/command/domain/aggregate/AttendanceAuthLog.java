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
@Table(name = "attendance_auth_log")
@Getter
public class AttendanceAuthLog extends BaseTimeEntity {

  //인증로그 id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "auth_log_id")
  private Integer authLogId;

  //근태
  @Column(name ="attendance_id", nullable = false)
  private Long attendanceId;

  //인증수단
  @Column(name = "auth_type", nullable = false, length = 50)
  private String authType;

  //인증 여부
  @Enumerated(EnumType.STRING)
  @Column(name = "auth_result", nullable = false)
  private Result result = Result.SUCCESS;

  //인증 시각
  @Column(name = "acted_at", nullable = false)
  private LocalDateTime actedAt;

  //접속ip주소
  @Column(name = "ip_address", nullable = false, length = 45)
  private String ip;

  //MAC주소
  @Column(name = "mac_address", length = 50)
  private String mac;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';
}
