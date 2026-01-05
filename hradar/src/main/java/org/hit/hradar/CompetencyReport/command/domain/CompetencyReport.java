package org.hit.hradar.CompetencyReport.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="competency_report")
@NoArgsConstructor
public class CompetencyReport {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "competency_report_id")
  private Long competencyReportId;

  @Column(name = "emp_id", nullable = false)
  private Long empId;

  @Column(name = "year", nullable = false)
  private Integer year;

  @Enumerated(EnumType.STRING)
  @Column(name = "quarter")
  private Quarter  quarter;

  @Column(name = "kpi_okr_result_summary", nullable = false)
  private String kpiOkrResultSummary;

  @Column(name = "goal_failure_analysis", nullable = false)
  private String goalFailureAnalysis;



}
