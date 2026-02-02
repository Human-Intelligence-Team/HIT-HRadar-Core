package org.hit.hradar.domain.competencyReport.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomCodeDTO {

  private Long customCodeId;
  private String groupCode;
  private String groupName;
  private String customCode;
  private String customName;
  private Character isDeleted;

  public CustomCodeDTO(Long customCodeId, String groupCode, String groupName) {
    this.customCodeId = customCodeId;
    this.groupCode = groupCode;
    this.groupName = groupName;
  }

  public CustomCodeDTO(Long customCodeId, String groupCode, String customCode, String customName, Character isDeleted) {
    this.customCodeId = customCodeId;
    this.groupCode = groupCode;
    this.customCode = customCode;
    this.customName = customName;
    this.isDeleted = isDeleted;
  }

}
