package org.hit.hradar.domain.salary.command.application.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.salary.command.application.dto.SalaryDTO;

@Getter
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
  private List<ApprovalLineStepDTO> approvalLineSteps;

  //  연봉 결재 (사원 테이블)
  private List<SalaryDTO> salaries;


  // 모든 필드를 파라미터로 받는 생성자
  public CommonApprovalRequest(Long docId, Long deptId, Long writerId, Long comId,
      String approvalDocumentType, String title, String content,
      LocalDateTime submittedDate, List<Long> referenceIds,
      List<ApprovalLineStepDTO> approvalLineSteps, List<SalaryDTO> salaries) {
    this.docId = docId;
    this.deptId = deptId;
    this.writerId = writerId;
    this.comId = comId;
    this.approvalDocumentType = approvalDocumentType;
    this.title = title;
    this.content = content;
    this.submittedDate = submittedDate;
    this.referenceIds = referenceIds;
    this.approvalLineSteps = approvalLineSteps;
    this.salaries = salaries;
  }
}