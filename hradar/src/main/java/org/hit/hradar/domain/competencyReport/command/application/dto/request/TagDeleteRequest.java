package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TagDeleteRequest {

  private List<Long> tagIds;

}
