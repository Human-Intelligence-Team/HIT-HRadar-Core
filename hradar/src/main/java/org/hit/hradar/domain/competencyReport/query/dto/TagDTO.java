package org.hit.hradar.domain.competencyReport.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDTO {

  private Long tagId;
  private String tagName;
  private Integer TagCount;

}
