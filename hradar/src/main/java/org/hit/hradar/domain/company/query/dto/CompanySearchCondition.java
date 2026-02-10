package org.hit.hradar.domain.company.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 회사 목록 조회 검색 조건 DTO
 * - Spring이 QueryString을 이 DTO 필드에 자동 바인딩한다.
 */
@Getter
@Setter
@NoArgsConstructor
public class CompanySearchCondition {

  // 검색어(회사명/사업자번호/회사코드)
  private String keyword;

  // 삭제 포함 여부(관리자용)
  private Boolean includeDeleted;
}
