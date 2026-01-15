package org.hit.hradar.domain.competencyReport.query.dto.response;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;

@Getter
public class TagSearchResponse {

  private List<TagDTO> tags;

  private TagSearchResponse(List<TagDTO> tags) {
    this.tags = tags;
  }

  public static TagSearchResponse tags(List<TagDTO> tags) {
    return new TagSearchResponse(tags);
  }
}
