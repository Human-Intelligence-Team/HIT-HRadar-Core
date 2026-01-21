package org.hit.hradar.domain.salary.query.dto.request;

import lombok.Getter;

@Getter
public class CompensationHistorySearchRequest {

  private String year;  // 년도
  private String compensationCode; // 변동 보상 타입
  private Long empId;

  public CompensationHistorySearchRequest(String year, String compensationCode, Long empId) {
    this.year = year;
    this.compensationCode = compensationCode;
    this.empId = empId;
  }

  public void setEmpId(Long empId) {
    this.empId = empId;
  }

}
