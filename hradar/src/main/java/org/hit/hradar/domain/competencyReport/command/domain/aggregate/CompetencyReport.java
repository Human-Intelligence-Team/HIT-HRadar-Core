package org.hit.hradar.domain.competencyReport.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Getter
@Table(name="competency_report")
@NoArgsConstructor
public class CompetencyReport extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "competency_report_id")
  private Long competencyReportId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "cycle_id", nullable = false)
  private Long cycleId;

  // 시작일
  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  // 종료일
  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "kpi_okr_result_summary", nullable = false)
  private String kpiOkrResultSummary;

  @Column(name = "goal_failure_analysis", nullable = false)
  private String goalFailureAnalysis;

  @Column(name = "is_deleted", nullable= false , columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  @PrePersist
  public void prePersist() {
    if (this.isDeleted == null) {
      this.isDeleted = 'N';
    }
  }

  public CompetencyReport(Long empId, Long cycleId, LocalDate startDate, LocalDate endDate,  String kpiOkrResultSummary, String goalFailureAnalysis, Character isDeleted) {
    this.empId = empId;
    this.cycleId = cycleId;
    this.startDate = startDate;
    this.endDate = endDate;
    this.kpiOkrResultSummary = kpiOkrResultSummary;
    this.goalFailureAnalysis = goalFailureAnalysis;
    this.isDeleted = isDeleted;
  }

  public static void create(Long empId, Long cycleId, LocalDate startDate, LocalDate endDate,
      String kpiOkrResultSummary, String goalFailureAnalysis) {
    new CompetencyReport(empId,  cycleId, startDate, endDate, kpiOkrResultSummary, goalFailureAnalysis, 'N');
  }

}
