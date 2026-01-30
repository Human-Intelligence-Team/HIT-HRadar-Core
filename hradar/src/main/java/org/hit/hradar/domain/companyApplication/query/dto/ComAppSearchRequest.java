package org.hit.hradar.domain.companyApplication.query.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComAppSearchRequest {

  // 회사명 부분검색
  private String companyName;

  // 사업자번호 정확검색 또는 부분검색(정책 선택)
  private String bizNo;

  // 상태 필터 (SUBMITTED/APPROVED/REJECTED)
  private String status;


}
