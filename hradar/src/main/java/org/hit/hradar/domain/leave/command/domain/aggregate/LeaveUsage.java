package org.hit.hradar.domain.leave.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.Getter;

@Table(name = "leave_usage")
@Entity
@Getter
public class LeaveUsage {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long usageId;

  @Column(name = "leave_id", nullable = false)
  private Long leaveId;

  @Column(name = "grant_id", nullable = false)
  private Long grantId;

  @Column(name = "use_date", nullable = false)
  private LocalDate useDate;

  @Column(name = "used_days", nullable = false)
  private double usedDays;

  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  // LeaveUsage.java (팩토리 메서드 추가)
  public static LeaveUsage create(
      Long leaveId,
      Long grantId,
      double usedDays,
      LocalDate useDate
  ) {
    LeaveUsage usage = new LeaveUsage();
    usage.leaveId = leaveId;
    usage.grantId = grantId;
    usage.usedDays = usedDays;
    usage.useDate = useDate;
    usage.isDeleted = 'N';
    return usage;
  }
}