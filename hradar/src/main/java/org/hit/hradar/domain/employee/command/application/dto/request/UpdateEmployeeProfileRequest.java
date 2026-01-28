package org.hit.hradar.domain.employee.command.application.dto.request;

import java.time.LocalDate;
import lombok.*;
import org.hit.hradar.domain.employee.command.domain.aggregate.Gender;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEmployeeProfileRequest {
  private String name;       // required
  private String email;      // nullable
  private Gender gender;     // nullable
  private String birth;      // nullable
  private LocalDate hireDate;// nullable
  private LocalDate exitDate;// nullable
  private String image;      // nullable
  private String extNo;      // nullable
  private String phoneNo;    // nullable
}
