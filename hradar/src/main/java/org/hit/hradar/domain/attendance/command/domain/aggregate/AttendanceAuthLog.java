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

  //근태 id
  //인증 성공시 존재, 인증 실패 시에는 NULL가능(근태 생성 전)
  @Column(name ="attendance_id", nullable = false)
  private Long attendanceId;

  //인증수단
  @Column(name = "auth_type", nullable = false, length = 50)
  private String authType;

  //인증 결과
  //SUCCESS 출/퇴근 처리를 위한 인증 성공
  //FAIL    출/퇴근 처리를 위한 인증 실패 (IP 차단 등)
  @Enumerated(EnumType.STRING)
  @Column(name = "auth_result", nullable = false)
  private Result result = Result.SUCCESS;

  //인증이 시도된 시각
  @Column(name = "acted_at", nullable = false)
  private LocalDateTime actedAt;

  //인증 요청을 보낸 클라이언트 IP주소
  @Column(name = "ip_address", nullable = false, length = 45)
  private String ip;

  //MAC주소
  @Column(name = "mac_address", length = 50)
  private String mac;

  //소프트 삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  //인증 성공 로그
  public static AttendanceAuthLog success(
      Long attendanceId,
      String authType,
      String ip
  ) {
    AttendanceAuthLog log = new AttendanceAuthLog();
    log.attendanceId = attendanceId;
    log.authType = authType;
    log.result = Result.SUCCESS;
    log.actedAt = LocalDateTime.now();
    log.ip = ip;
    log.isDeleted = 'N';
    return log;
  }

  //인증 실패 로그(근태 로그는 생성되지 않음)
  public static AttendanceAuthLog fail(
      String authType,
      String ip
  ) {
    AttendanceAuthLog log = new AttendanceAuthLog();
    log.authType = authType;
    log.result = Result.FAIL;
    log.actedAt = LocalDateTime.now();
    log.ip = ip;
    log.isDeleted = 'N';
    return log;
  }
}
