package org.hit.hradar.domain.user.query.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountLoginIdResponse {
  private Long accId;
  private String loginId;
  private String name;
  private String email;
}
