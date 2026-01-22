package org.hit.hradar.domain.companyApplication.command.application.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveComAppResponse {

  private Long appId;           // 신청서 ID
  private String status;        // APPROVED 등
  private Long comId;           // 생성된 회사 PK
  private String companyCode;   // 발급된 회사코드(프론트 전달용)
  private String loginId;       // 생성된 관리자 로그인 ID
  private String password;      // 임시 비밀번호(초기 전달용)
}
