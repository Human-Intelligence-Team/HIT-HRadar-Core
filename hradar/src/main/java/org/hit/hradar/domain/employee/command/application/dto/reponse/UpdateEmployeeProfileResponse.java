package org.hit.hradar.domain.employee.command.application.dto.reponse;

import java.time.LocalDate;
import lombok.*;
import org.hit.hradar.domain.employee.command.domain.aggregate.Gender;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeProfileResponse {
  private Long empId;
  private String name;
  private String email;
  private Gender gender;
  private String birth;
  private LocalDate hireDate;
  private LocalDate exitDate;
  private String image;
  private String extNo;
  private String phoneNo;
}
