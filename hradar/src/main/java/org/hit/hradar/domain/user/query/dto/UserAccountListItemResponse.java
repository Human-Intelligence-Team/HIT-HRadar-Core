package org.hit.hradar.domain.user.query.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountListItemResponse {

  private Long accId;
  private Long comId;
  private String comCode;

  private Long empId;

  private String loginId;
  private String name;
  private String email;

  private String role;
  private String status;
  private Character isDeleted;
}
