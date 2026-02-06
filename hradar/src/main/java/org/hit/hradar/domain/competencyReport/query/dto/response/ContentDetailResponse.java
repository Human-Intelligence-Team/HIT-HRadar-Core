package org.hit.hradar.domain.competencyReport.query.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.ContentDTO;

@Getter
@NoArgsConstructor
public class ContentDetailResponse {

  private ContentDTO content;

  public  ContentDetailResponse(ContentDTO content) {
    this.content = content;
  }

}
