package org.hit.hradar.domain.salary.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BasicSalarySummaryDTO {

  private String year;
  private Long totalAmount;

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public BasicSalarySummaryDTO(String year, Long totalAmount) {
    this.year = year;
    this.totalAmount = totalAmount;
  }

  public void setYear(String year) {
    this.year = year;
  }
}
