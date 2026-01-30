package org.hit.hradar.domain.rolePermission.command.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdateRoleRequest {

  private String name;
  private List<Long> permIds;

  public UpdateRoleRequest(String name, List<Long> permIds) {
    this.name = name;
    this.permIds = permIds;
  }
}