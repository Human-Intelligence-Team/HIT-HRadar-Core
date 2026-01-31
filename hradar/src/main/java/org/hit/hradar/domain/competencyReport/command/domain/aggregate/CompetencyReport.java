package org.hit.hradar.domain.competencyReport.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
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




}
