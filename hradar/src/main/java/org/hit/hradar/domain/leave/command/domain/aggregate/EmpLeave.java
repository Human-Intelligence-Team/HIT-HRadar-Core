package org.hit.hradar.domain.leave.command.domain.aggregate;

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

@Table(name = "emp_leave")
@Entity
@Getter
public class EmpLeave extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "leave_id")
  private Long leaveId;

  //사원 ID
  @Column(name = "emp_id", nullable = false)
  private Long empId;

  //휴가 유형(연차/반차/반반차 saas)
  @Enumerated(EnumType.STRING)
  @Column(name = "leave_type", nullable = false)
  private String leaveType;

  //휴가 기간
  @Enumerated(EnumType.STRING)
  @Column(name = "leave_unit_type", nullable = false)
  private double leaveUnitType;

  //휴가 사유
  @Column(name = "reason")
  private String reason;

  //휴가 시작일
  @Column(name = "start_date", nullable = false)
  private LocalDateTime startDate;

  //휴가 종료일
  @Column(name = "end_date", nullable = false)
  private LocalDateTime endDate;

  //휴가 사용일수
  @Column(name = "leave_days", nullable = false)
  private double leaveDays;

  //휴가 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "leave_status", nullable = false)
  private LeaveStatus leaveStatus = LeaveStatus.ON_LEAVE;


  //삭제 여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';







}
