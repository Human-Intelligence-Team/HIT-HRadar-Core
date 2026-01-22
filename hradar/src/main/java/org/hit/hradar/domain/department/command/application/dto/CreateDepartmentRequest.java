package org.hit.hradar.domain.department.command.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateDepartmentRequest {
    @NotBlank
    private String deptName;
    private Long parentDeptId;
    private Long managerEmpId;
    @NotBlank
    private String deptPhoneNo;
}


