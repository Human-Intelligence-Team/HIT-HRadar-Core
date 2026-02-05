package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SalaryApprovalRequest {

  private String approvalDocType;
  private Long comId;
  private Long docId;



  public void setDocId(Long docId) {
    this.docId = docId;
  }

  public void setComId(Long comId) {
    this.comId = comId;
  }

  public SalaryApprovalRequest(Long comId, Long docId) {
    this.comId = comId;
    this.docId = docId;
  }

  public SalaryApprovalRequest(String approvalDocType, Long comId) {
    this.approvalDocType = approvalDocType;
    this.comId = comId;
  }
}
