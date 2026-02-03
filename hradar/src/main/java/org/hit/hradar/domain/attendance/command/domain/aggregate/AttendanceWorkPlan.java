package org.hit.hradar.domain.attendance.command.domain.aggregate;

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
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "attendance_work_plan")
@Getter
@NoArgsConstructor
public class AttendanceWorkPlan extends BaseTimeEntity {


  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "work_plan_id")
  private Long workPlanId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "work_type", nullable = false, length = 50)
  private String workType;

  @Column(name = "location", nullable = false, length = 50)
  private String location;

  @Column(name = "start_at", nullable = false)
  private LocalDateTime startAt;

  @Column(name = "end_at", nullable = false)
  private LocalDateTime endAt;

  // 초과근무 (없으면 null)
  @Column(name = "overtime_minutes")
  private Integer overtimeMinutes;

  // 결재 문서 ID
  @Column(name = "doc_id", nullable = false)
  private Long docId;

  // 신청 상태
  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 30)
  private ApprovalStatus status = ApprovalStatus.REQUESTED;

  // 삭제 여부
  @Column(name = "is_deleted", nullable = false)
  private Character isDeleted = 'N';

  public static AttendanceWorkPlan create(
      Long  empId,
      Long docId,
      String workType,
      String location,
      LocalDateTime startAt,
      LocalDateTime endAt,
      Integer overtimeMinutes
      ) {
    AttendanceWorkPlan plan = new AttendanceWorkPlan();
    plan.empId  = empId;
    plan.docId = docId;
    plan.workType = workType;
    plan.location = location;
    plan.startAt = startAt;
    plan.endAt = endAt;
    plan.overtimeMinutes = overtimeMinutes;
    plan.status = ApprovalStatus.REQUESTED;
    return plan;

  }
  public void approve() {
    this.status = ApprovalStatus.APPROVED;
  }

  public void reject() {
    this.status = ApprovalStatus.REJECTED;
  }


}
