package org.hit.hradar.domain.competencyReport.query.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.domain.competencyReport.query.dto.CustomCodeDTO;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomCodeSearchResponse {

  private List<CustomCodeDTO> customCodes;

  private  List<CustomCodeDTO> levelCodes;
  private  List<CustomCodeDTO> typeCodes;

  public CustomCodeSearchResponse( List<CustomCodeDTO> customCodes ) {
    this.customCodes = customCodes;
  }

  public CustomCodeSearchResponse( List<CustomCodeDTO> levelCodes, List<CustomCodeDTO> typeCodes) {
    this.levelCodes = levelCodes;
    this.typeCodes = typeCodes;
  }

}
