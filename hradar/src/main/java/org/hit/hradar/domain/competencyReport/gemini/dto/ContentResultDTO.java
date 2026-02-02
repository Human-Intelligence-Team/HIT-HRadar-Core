package org.hit.hradar.domain.competencyReport.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter

public class ContentResultDTO {

  private Long contentId;
  private String reason;


  public ContentResultDTO(Long contentId, String reason) {
    this.contentId = contentId;
    this.reason = reason;
  }

}
