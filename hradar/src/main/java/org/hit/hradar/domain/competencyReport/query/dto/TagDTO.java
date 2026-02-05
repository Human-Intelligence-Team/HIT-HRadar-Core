package org.hit.hradar.domain.competencyReport.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class TagDTO {

  private Long tagId;
  private String tagName;
  private Integer TagCount;

  public  TagDTO(Long tagId, String tagName, Integer TagCount) {
    this.tagId = tagId;
    this.tagName = tagName;
    this.TagCount = TagCount;
  }

  public  TagDTO(Long tagId, String tagName) {
    this.tagId = tagId;
    this.tagName = tagName;
  }
}
