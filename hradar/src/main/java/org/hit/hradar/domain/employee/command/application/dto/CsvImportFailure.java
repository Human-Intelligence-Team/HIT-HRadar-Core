package org.hit.hradar.domain.employee.command.application.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CsvImportFailure {
  private int rowNumber;     // 1부터
  private String reason;
  private String rawLine;    // 디버그용
}
