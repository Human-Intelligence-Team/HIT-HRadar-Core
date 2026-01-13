package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import java.util.List;
import lombok.Getter;
import org.hit.hradar.domain.competencyReport.query.dto.TagDTO;

@Getter
public class TagDeleteRequest {

  private List<Long> tagIds;

}
