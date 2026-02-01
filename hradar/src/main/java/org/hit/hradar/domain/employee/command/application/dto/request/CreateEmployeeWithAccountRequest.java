package org.hit.hradar.domain.employee.command.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 수동 사원 + 계정 생성 요청 DTO
 * - 화면에서 사원정보 + 계정정보 + 초기 비밀번호를 함께 입력받는다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEmployeeWithAccountRequest {

  @NotBlank
  private String employeeNo;

  @NotBlank
  private String name;

  private String phone;

  @NotBlank
  private String loginId;

  @NotBlank
  private String email;

  @NotBlank
  private String password;

  private Long deptId;
  private Long positionId;
  private String gender;
  private String birth;
  private String hireDate;

}
