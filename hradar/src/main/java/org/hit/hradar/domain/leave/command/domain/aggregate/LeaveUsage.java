package org.hit.hradar.domain.leave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "LEAVE_USAGE")
@Getter
public class LeaveUsage extends BaseTimeEntity {

  //연차 이력id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "usage_id")
  private Long usageId;

  //휴가id
  @Column(name ="leave_id", nullable = false)
  private Long leaveId;

  //연차id
  @Column(name = "grant_id", nullable = false)
  private Long grantId;

  //차감 기준일
  @Column(name = "use_date", nullable = false)
  private LocalDate useDate;

  //차감 연차
  @Column(name = "used_days")
  private double usedDays;

  //생성자

  //수정자

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private String isDeleted;
}
