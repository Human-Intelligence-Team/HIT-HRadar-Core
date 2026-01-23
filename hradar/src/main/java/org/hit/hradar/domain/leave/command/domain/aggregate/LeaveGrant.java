package org.hit.hradar.domain.leave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import lombok.Getter;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table
@Getter
public class LeaveGrant extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "grant_id")
  private Long grantId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "year", nullable = false)
  private int year;

  @Column(name = "tatal_Days", nullable = false)
  private double totalDays;

  @Column(name = "reamaining_Days", nullable = false)
  private double remainingDays;

  //연차 발생일
  @Column(name = "granted_days")
  private LocalDate grantedDays;

  //연차 만료일
  @Column(name = "expire_date")
  private Date expireDate;

  //삭제여부
  private Character isDeleted = 'N';


}
