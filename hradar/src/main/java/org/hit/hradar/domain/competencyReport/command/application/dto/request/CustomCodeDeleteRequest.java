package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomCodeDeleteRequest {

  private List<Long> customCodeIds;

}
