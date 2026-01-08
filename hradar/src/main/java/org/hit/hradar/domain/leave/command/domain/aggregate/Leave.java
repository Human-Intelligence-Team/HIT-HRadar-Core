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

@Entity
@Table(name = "EMP_LEAVE")
@Getter
public class Leave extends BaseTimeEntity {

  //휴가id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "leave_id")
  private Long leaveId;

  //사원id
  @Column(name = "emp_id", nullable = false)
  private Long empId;

  //휴가 유형
  @Column(name = "leave_type", nullable = false, length = 50)
  private String leaveType;

  //휴가 사유
  @Column(name = "reason", nullable = false, length = 255)
  private String reason;

  //시작일
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  //종료일
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  //사용 일수
  @Column(name = "leave_days", nullable = false)
  private double leaveDays;

  //휴가 상태
  @Column(name = "status", nullable = false)
  private String status;

  //휴가 신청
  @Column(name = "requested_at", nullable = false)
  private LocalDateTime requestedAt;

  //휴가 승인
  @Column(name = "approved_at")
  private LocalDateTime approvedAt;

  //반려 사유
  @Column(name = "rejected_reason")
  private String rejectedReason;

  //생성자

  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private String isDeleted;

}
