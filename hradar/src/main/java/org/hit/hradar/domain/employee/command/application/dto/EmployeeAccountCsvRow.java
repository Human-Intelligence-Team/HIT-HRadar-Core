package org.hit.hradar.domain.employee.command.application.dto;

import java.time.LocalDate;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeAccountCsvRow {
  // Employee
  private String name;
  private String employeeNo;
  private String email;
  private Long deptId;       // optional
  private Long positionId;   // optional
  private String gender;     // optional
  private LocalDate birth;   // optional
  private LocalDate hireDate;// optional
  private String phoneNo;    // optional

  // Account
  private String loginId;    // required (or optional이면 employeeNo로 대체)
  private String password;   // required
}
