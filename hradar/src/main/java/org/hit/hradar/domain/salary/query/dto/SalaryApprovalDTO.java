package org.hit.hradar.domain.salary.query.dto;

import java.time.LocalDate;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalStatus;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;
import org.hit.hradar.domain.salary.command.domain.aggregate.SalaryIncreaseType;

@Getter
@Setter
@NoArgsConstructor
public class SalaryApprovalDTO {

  private String year; // 년도
  private String docId;
  private String title;
  private SalaryIncreaseType salaryIncreaseType;
  private CompensationType compensationType;
  private String remark;
  private LocalDate approvedAt;
  private String approvalStatus;
  private String writer;
  private Long totalSalary;
  private Long empCount;


  public SalaryApprovalDTO(String year, String docId, String title,LocalDate approvedAt,String approvalStatus,String writer ,Long totalSalary ,SalaryIncreaseType salaryIncreaseType, Long empCount) {
    this.year = year;
    this.docId = docId;
    this.title = title;
    this.approvedAt = approvedAt;
    this.approvalStatus = approvalStatus;
    this.writer = writer;
    this.totalSalary = totalSalary;
    this.salaryIncreaseType = salaryIncreaseType;
    this.empCount = empCount;
  }

  public SalaryApprovalDTO(String year, String docId, String title,
      CompensationType compensationType,
      LocalDate approvedAt, String approvalStatus, String writer, Long totalSalary, String remark
      , Long empCount) {
    this.year = year;
    this.docId = docId;
    this.title = title;
    this.compensationType = compensationType;
    this.approvedAt = approvedAt;
    this.approvalStatus = approvalStatus;
    this.writer = writer;
    this.totalSalary = totalSalary;
    this.remark = remark;
    this.empCount = empCount;
  }
}
