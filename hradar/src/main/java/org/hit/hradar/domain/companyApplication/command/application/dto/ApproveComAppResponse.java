package org.hit.hradar.domain.companyApplication.command.application.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApproveComAppResponse {
  private Long applicationId;
  private Long companyId;

  private String adminLoginId;
  private String tempPassword;  // 플랫폼이 발급한 임시 비밀번호 (서면 전달용),비밀번호는 화면에 1회 노출
}