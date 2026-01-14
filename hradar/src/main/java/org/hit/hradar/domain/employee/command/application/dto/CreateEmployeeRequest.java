package org.hit.hradar.domain.employee.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateEmployeeRequest {

  @NotBlank @Size(max = 30)
  private String employeeNo;

  @NotBlank @Size(max = 100)
  private String loginId;

  @NotBlank @Size(min = 8, max = 100)
  private String password; // 최고관리자가 지정

  @NotBlank @Size(max = 100)
  private String name;
}
