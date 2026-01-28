package org.hit.hradar.domain.employee.command.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFirstEmpRequest {
  private Long comId;
  private String name;
  private String email;

}
