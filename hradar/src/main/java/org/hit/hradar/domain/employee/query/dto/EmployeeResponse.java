package org.hit.hradar.domain.employee.query.dto;

import java.time.LocalDate;
import lombok.*;
import org.hit.hradar.domain.employee.command.domain.aggregate.EmploymentType;
import org.hit.hradar.domain.employee.command.domain.aggregate.Gender;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeResponse {

  private Long empId;
  private Long comId;
  private Long deptId;
  private Long positionId;

  private String name;
  private String employeeNo;
  private String email;

  private Gender gender;
  private String birth;
  private LocalDate hireDate;
  private LocalDate exitDate;

  private String image;
  private String extNo;
  private String phoneNo;
  private String note;

  private EmploymentType employmentType;
  private Character isDeleted;

  private Long accId;
  private String loginId;
  private String role;
  private String status;
}
