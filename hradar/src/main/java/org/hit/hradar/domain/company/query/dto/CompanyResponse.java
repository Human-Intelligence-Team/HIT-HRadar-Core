package org.hit.hradar.domain.company.query.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

  // 회사 PK
  private Long companyId;

  // 회사 코드
  private String comCode;

  // 회사명
  private String companyName;

  // 사업자번호
  private String bizNo;

  // 대표 연락처
  private String comTel;

  // 주소
  private String address;

  // 상태(승인/반려 등) - 문자열로 내려주면 enum 변경에도 유연
  private String status;

  // 소프트 삭제 여부(Y/N)
  private Character isDeleted;
}
