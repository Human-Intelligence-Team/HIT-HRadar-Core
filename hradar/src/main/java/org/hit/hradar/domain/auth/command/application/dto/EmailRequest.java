  package org.hit.hradar.domain.auth.command.application.dto;

import lombok.Builder;
import lombok.Getter;
import org.hit.hradar.domain.auth.command.domain.aggregate.VerificationPurpose;

  @Getter
@Builder
public class EmailRequest {

  private String email;
  private VerificationPurpose purpose;
}
