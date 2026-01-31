package org.hit.hradar.domain.approval.query.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApprovalDocumentTypeResponse {

  private Long docId;
  private String docType;
  private String name;
  private boolean active;
}