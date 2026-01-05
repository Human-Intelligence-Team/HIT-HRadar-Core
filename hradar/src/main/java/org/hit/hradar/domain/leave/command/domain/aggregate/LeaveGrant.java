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
@Table(name = "LEAVE_GRANT")
@Getter
public class LeaveGrant {

  //연차id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "grant_id")
  private Long grantId;

  //연차 적용년도
  @Column(name = "year", nullable = false)
  private Integer year;

  //총 연차
  @Column(name = "total_days")
  private double totalDays;

  //남은 연차
  @Column(name = "remaining_days")
  private double remainingDays;

  //연차 부여된 시각
  @Column(name = "granted_at")
  private LocalDate grantedAt;

  /*사원id
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="emp_id", nullable = false)
  private Employee employee;
  */
}
