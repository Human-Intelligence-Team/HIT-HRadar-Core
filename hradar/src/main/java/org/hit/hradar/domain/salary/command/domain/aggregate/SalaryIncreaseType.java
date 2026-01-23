package org.hit.hradar.domain.salary.command.domain.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SalaryIncreaseType {

  REGULAR("정기 인상"),
  PERFORMANCE("성과 인상"),
  PROMOTION("승진 인상"),
  ADJUSTMENT("연봉 보정"),
  SPECIAL("특별 인상");

  private final String description;


}
