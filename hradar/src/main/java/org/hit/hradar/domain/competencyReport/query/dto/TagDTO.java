package org.hit.hradar.domain.competencyReport.query.dto;

import lombok.Getter;

@Getter
public class TagDTO {

  private Long tagId;
  private String tagName;

  public TagDTO(Long tagId, String tagName) {
    this.tagId = tagId;
    this.tagName = tagName;
  }
}
