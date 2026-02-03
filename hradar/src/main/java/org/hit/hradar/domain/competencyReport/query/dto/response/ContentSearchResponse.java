package org.hit.hradar.domain.competencyReport.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;

@Getter
public class ContentSearchResponse {

  private List<ContentDTO> contents;

  public ContentSearchResponse(List<ContentDTO> contents) {
    this.contents = contents;
  }

}
