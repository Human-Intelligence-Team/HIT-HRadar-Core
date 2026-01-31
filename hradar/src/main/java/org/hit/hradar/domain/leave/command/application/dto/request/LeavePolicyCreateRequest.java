package org.hit.hradar.domain.leave.command.application.dto.request;

import lombok.Getter;

@Getter
public class LeavePolicyCreateRequest {
  private String typeCode;
  private String typeName;
  private String unitCode;
  private double unitDays;
}
