package org.hit.hradar.domain.competencyReport.command.application.dto.response;

import lombok.Getter;

@Getter
public class ContentUpdateResponse {

  private Long ContentId;

  public ContentUpdateResponse(Long ContentId) {
    this.ContentId = ContentId;
  }
}
