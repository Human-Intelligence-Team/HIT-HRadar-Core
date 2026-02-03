package org.hit.hradar.domain.employee.command.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCsvRegisterRequest {
    private String name;
    private String email;
    private String phoneNo;
    private String employeeNo;
    private String loginId;
    private String password;
    private String hireDate; // YYYY-MM-DD
    private String deptName;
    private String positionName;
    private String gender; // M/F
    private String birth; // YYYY-MM-DD
}
