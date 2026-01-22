package org.hit.hradar.domain.employee.command.application.dto;

import java.util.List;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CsvImportEmployeesResponse {
  private int total;
  private int success;
  private int failed;
  private List<CsvImportFailure> failures;
}
