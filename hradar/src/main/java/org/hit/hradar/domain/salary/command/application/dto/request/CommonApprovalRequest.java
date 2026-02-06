package org.hit.hradar.domain.salary.command.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalSaveMode;
import org.hit.hradar.domain.salary.command.application.dto.SalaryDTO;
import org.hit.hradar.domain.salary.command.domain.aggregate.CompensationType;
import org.hit.hradar.domain.salary.command.domain.aggregate.SalaryIncreaseType;
import org.hit.hradar.domain.salary.query.dto.CompensationSalaryDTO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonApprovalRequest {

  // 결재 문서
  private Long docId;
  private Long deptId;
  private Long writerId;
  private Long comId;
  private String approvalDocumentType;
  private String title;
  private String content;
  private LocalDateTime submittedDate;

  // 참조자
  private List<Long> referenceIds;

  // 결재선
 // private List<ApprovalLineStepDTO> approvalLineSteps;
  private List<Long> approverIds;
  //  연봉 결재 (사원 테이블)
  private List<SalaryDTO> salaries;
  private List<SalaryDTO>  compensationSalaries;

  private ApprovalSaveMode  approvalSaveMode; // DRAFT, SUBMIT
  private SalaryIncreaseType salaryIncreaseType;
  private CompensationType compensationType;

  private String remark;

  // 모든 필드를 파라미터로 받는 생성자
  public CommonApprovalRequest(Long docId, Long deptId, Long writerId, Long comId,
      String approvalDocumentType, String title, String content,
      LocalDateTime submittedDate, List<Long> referenceIds,
      List<Long> approverIds, List<SalaryDTO> salaries, SalaryIncreaseType salaryIncreaseType,
      CompensationType compensationType , String remark) {
    this.docId = docId;
    this.deptId = deptId;
    this.writerId = writerId;
    this.comId = comId;
    this.approvalDocumentType = approvalDocumentType;
    this.title = title;
    this.content = content;
    this.submittedDate = submittedDate;
    this.referenceIds = referenceIds;
    this.approverIds = approverIds;
    this.salaries = salaries;
    this.salaryIncreaseType = salaryIncreaseType;
    this.compensationType = compensationType;
    this.remark = remark;
  }

}