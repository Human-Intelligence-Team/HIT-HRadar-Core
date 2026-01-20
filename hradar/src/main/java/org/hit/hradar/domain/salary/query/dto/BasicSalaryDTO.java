package org.hit.hradar.domain.salary.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class BasicSalaryDTO {

  private Long basicSalaryId;
  private Long docId;
  private String content;
  private Long empId;
  private Long basicSalary;
  private BigDecimal increaseRate;
  private Integer increaseAmount;
  private LocalDate approvedAt;

}
