package org.hit.hradar.domain.leave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Table(name = "emp_leave")
@Entity
@Getter
public class EmpLeave extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "leave_id")
  private Long leaveId;

  @Column(name = "docId", nullable = false)
  private Long docId;

  //사원 ID
  @Column(name = "emp_id", nullable = false)
  private Long empId;

  // 연차(grant)에서 차감할지
  @Column(name = "grant_id", nullable = false)
  private Long grantId;

  //휴가 유형(연차/병가/경조사/공가/기타 saas)
  @Column(name = "leave_type", nullable = false)
  private String leaveType;

  //휴가 기간(FULL_DAY/HALF_AM/HALF_PM/HOUR saas)
  @Column(name = "leave_unit_type", nullable = false)
  private String leaveUnitType;

  //휴가 사유
  @Column(name = "reason")
  private String reason;

  //휴가 시작일
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  //휴가 종료일
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  //휴가 사용일수
  @Column(name = "leave_days", nullable = false)
  private double leaveDays;

  //휴가 신청 시각
  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  //삭제 여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  protected EmpLeave() {}

  //휴가 저장
  public static EmpLeave create(
      Long docId,
      Long empId,
      Long grantId,
      String leaveType,
      String leaveUnitType,
      String reason,
      LocalDate startDate,
      LocalDate endDate,
      double leaveDays
  ) {
    EmpLeave leave = new EmpLeave();
    leave.docId = docId;
    leave.empId = empId;
    leave.grantId = grantId;
    leave.leaveType = leaveType;
    leave.leaveUnitType = leaveUnitType;
    leave.reason = reason;
    leave.startDate = startDate;
    leave.endDate = endDate;
    leave.leaveDays = leaveDays;
    leave.requestedAt = LocalDateTime.now();
    leave.isDeleted = 'N';
    return leave;
  }
}
