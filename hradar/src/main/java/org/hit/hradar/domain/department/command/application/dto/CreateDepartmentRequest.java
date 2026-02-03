package org.hit.hradar.domain.department.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentRequest {


  @NotBlank(message = "부서명(deptName)은 필수입니다.")
  @Size( max = 40 )
  private String deptName;

  private Long parentDeptId;
  private Long managerEmpId;

  @NotBlank(message = "부서 연락처(deptPhoneNo)는 필수입니다.")
  @Size( max = 20 )
  private String deptPhone;
}
