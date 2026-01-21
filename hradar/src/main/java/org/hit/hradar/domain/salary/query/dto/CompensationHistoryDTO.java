package org.hit.hradar.domain.salary.query.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CompensationHistoryDTO {

  private String year;  // 년도
  private String compensationCode; // 변동 보상 코드
  private String compensationType; // 변동 보상 타입
  private String title;
  private String amount;
  private String docType;
  private BigDecimal rate;
  private LocalDate approvedAt;
  private Long empId;

}
