package org.hit.hradar.domain.empLeave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Entity
@Table(name = "leave_grant")
@Getter
public class LeaveGrant {

  //연차id
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "grant_id")
  private Long grantId;

  //사원id
  @Column(name ="emp_id", nullable = false)
  private String employee;

  //연차 적용년도
  @Column(name = "year", nullable = false)
  private Integer year;

  //총 연차
  @Column(name = "total_days")
  private double totalDays;

  //남은 연차
  @Column(name = "remaining_days")
  private double remainingDays;

  //연차 부여된 날짜
  @Column(name = "granted_at")
  private LocalDate grantedAt;

  //연차 만료일
  @Column(name = "expire_date")
  private LocalDate expireDate;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

}
