package org.hit.hradar.domain.salary.query.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryApprovalRequest {

  private String approvalDocType;
  private Long comId;
  private Long docId;
  private String year;
  private Long empId;


  public void setEmpId(Long empId) {
    this.empId = empId;
  }

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
