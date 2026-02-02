package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import lombok.Getter;

@Getter
public class CustomCodeSearchRequest {

  private Long customCodeId;

  public CustomCodeSearchRequest(Long customCodeId) {
    this.customCodeId = customCodeId;
  }
}
