package org.hit.hradar.domain.company.command.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

//회사 수정 dto
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCompanyRequest {

  @NotBlank
  private String comName;   // 회사명

  @NotBlank
  private String bizNo;     // 사업자번호 (변경 가능 정책이면 유지, 아니면 제거)

  private String address;   // 주소

  private String comTel;    // 전화번호

  private String foundDate;
}
