package org.hit.hradar.domain.companyApplication.query.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComAppSearchRequest {

  // 회사명 (부분검색)
  private String companyName;

  // 사업자번호 (부분검색 or 하이픈 포함해도 LIKE로 검색됨)
  private String bizNo;
}
