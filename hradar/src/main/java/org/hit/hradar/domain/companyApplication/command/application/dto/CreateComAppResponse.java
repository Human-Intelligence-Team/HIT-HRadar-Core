package org.hit.hradar.domain.companyApplication.command.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateComAppResponse {
  private Long applicationId;
  private String companyApplicationStatus;
}
