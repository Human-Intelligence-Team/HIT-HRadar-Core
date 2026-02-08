package org.hit.hradar.domain.competencyReport.command.application.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CustomCodeSearchRequest {

  private Long comId;
  private Long customCodeId;
  private String groupCode;

  public void setComId(Long comId) {
    this.comId = comId;
  }

  public CustomCodeSearchRequest(Long customCodeId,  String GroupCode) {
    this.customCodeId = customCodeId;
    this.groupCode = GroupCode;
  }
}
