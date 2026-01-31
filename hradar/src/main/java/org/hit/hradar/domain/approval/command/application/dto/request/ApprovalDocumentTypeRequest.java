package org.hit.hradar.domain.approval.command.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApprovalDocumentTypeRequest {

  private String docType;
  private String name;
}
