package org.hit.hradar.domain.leave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Table(name = "LEAVE_USAGE")
@Getter
public class LeaveUsage {

  //연차 이력id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "usage_id")
  private Long usageId;

  //연차 사용 기준일
  @Column(name = "use_date")
  private LocalDate useDate;

  //차감 연차
  @Column(name = "used_days")
  private double usedDays;

  //휴가 id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="leave_id", nullable = false)
  private Leave leaveId;
}
