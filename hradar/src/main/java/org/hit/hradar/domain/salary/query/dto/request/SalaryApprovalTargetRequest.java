package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;
import org.hit.hradar.domain.approval.command.domain.aggregate.ApprovalDocumentType;
import org.hit.hradar.domain.document.command.domain.aggregate.DocumentType;

@Getter
public class SalaryApprovalTargetRequest {

  private ApprovalDocumentType approvalDocumentType;

  public SalaryApprovalTargetRequest(ApprovalDocumentType approvalDocumentType) {
    this.approvalDocumentType = approvalDocumentType;
  }
}
