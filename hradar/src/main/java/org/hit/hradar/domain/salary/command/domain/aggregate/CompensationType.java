package org.hit.hradar.domain.salary.command.domain.aggregate;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompensationType {
  PERFORMANCE("성과금") // 성과금
  , BONUS("상여금")   // 상여금
  , INCENTIVE("인센티브")   // 인센티브
  , ALLOWANCE("기타 수당")   // 기타 수당
;

  private final String description;
}
