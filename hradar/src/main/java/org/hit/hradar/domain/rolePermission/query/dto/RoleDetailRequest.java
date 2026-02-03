package org.hit.hradar.domain.rolePermission.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleDetailRequest {

  private Long roleId;

  public RoleDetailRequest(Long roleId) {
    this.roleId = roleId;
  }
}
