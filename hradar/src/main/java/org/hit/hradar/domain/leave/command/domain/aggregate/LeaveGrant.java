package org.hit.hradar.domain.leave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "leave_grant")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LeaveGrant extends BaseTimeEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "grant_id")
  private Long grantId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "year", nullable = false)
  private Integer year;

  @Column(name = "total_days", nullable = false)
  private double totalDays;

  @Column(name = "remaining_days", nullable = false)
  private double remainingDays;

  //연차 발생일
  @Column(name = "granted_days")
  private LocalDate grantedDays;

  //연차 만료일
  @Column(name = "expire_date")
  private LocalDate expireDate;

  //삭제여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  public static LeaveGrant create(Long empId, Integer year, double totalDays, LocalDate grantedDays, LocalDate expireDate) {

    return LeaveGrant.builder()
        .empId(empId)
        .year(year)
        .totalDays(totalDays)
        .remainingDays(totalDays) // Initial remaining days is same as total
        .grantedDays(grantedDays)
        .expireDate(expireDate)
        .isDeleted('N')
        .build();
  }
}

