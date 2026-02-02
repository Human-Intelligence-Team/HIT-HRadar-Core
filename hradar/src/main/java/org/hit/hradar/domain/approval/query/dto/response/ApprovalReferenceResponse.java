package org.hit.hradar.domain.approval.query.dto.response;

import lombok.Getter;

@Getter
public class ApprovalReferenceResponse {

  private Long referenceId;
  private Long referrerId;
  private String referrerName;

}
