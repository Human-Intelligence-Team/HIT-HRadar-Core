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


}
