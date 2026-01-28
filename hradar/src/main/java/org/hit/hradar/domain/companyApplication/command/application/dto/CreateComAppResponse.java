package org.hit.hradar.domain.companyApplication.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
public class CreateComAppResponse {                         //플랫폼 관리자의 승인,거절 여부
  private Long applicationId;
  private String companyApplicationStatus;
}
