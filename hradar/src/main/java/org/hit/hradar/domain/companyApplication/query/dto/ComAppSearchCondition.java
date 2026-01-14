package org.hit.hradar.domain.companyApplication.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hit.hradar.domain.companyApplication.command.domain.aggregate.CompanyApplicationStatus;

@Getter
@Setter
@NoArgsConstructor
public class ComAppSearchCondition {

  private CompanyApplicationStatus status; // null이면 전체

  // 검색 필드 (둘 다 null/blank면 전체 조회)
  private String companyName;
  private String bizNo;
  private String comAdminLoginId;
}
