package org.hit.hradar.domain.employee.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 회사 승인 시 생성된 회사생성자 Employee 생성 결과 DTO
 */
@Builder
@Getter
@AllArgsConstructor
public class CreateFirstEmpResponse {
  private Long empId;
  private Long comId;
  private String name;
  private String email;
}