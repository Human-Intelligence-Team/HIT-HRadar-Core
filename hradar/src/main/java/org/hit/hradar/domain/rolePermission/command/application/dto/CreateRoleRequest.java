package org.hit.hradar.domain.rolePermission.command.application.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateRoleRequest {

  private String name;
  private List<Long> permIds;

  public CreateRoleRequest(String name, List<Long> permIds) {
    this.name = name;
    this.permIds = permIds;
  }
}