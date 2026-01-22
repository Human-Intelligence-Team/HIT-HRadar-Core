package org.hit.hradar.domain.user.query.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OneTimeTempPasswordResponse {
  private Long appId;
  private String tempPassword; // 1회 노출
}
