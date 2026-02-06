package org.hit.hradar.domain.competencyReport.command.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CustomCodeExistResponse {

  private Boolean exist;

  public CustomCodeExistResponse(Boolean exist) {
    this.exist = exist;
  }
}
