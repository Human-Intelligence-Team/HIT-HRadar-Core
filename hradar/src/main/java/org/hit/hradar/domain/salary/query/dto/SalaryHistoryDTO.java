package org.hit.hradar.domain.salary.query.dto;

import java.time.LocalDate;
import lombok.Getter;

@Getter
public class SalaryHistoryDTO {

  private String empId;
  private String year;
  private String content;
  private String prevSalary;
  private String currentSalary;
  private Double increaseRete;
  private LocalDate approvedAt;

}
