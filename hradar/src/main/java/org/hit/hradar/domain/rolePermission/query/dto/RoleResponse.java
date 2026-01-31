package org.hit.hradar.domain.rolePermission.query.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleResponse {

  private Long roleId;
  private String name;
  private boolean isSystem;
  private String roleKey;

  private List<PermissionResponse> permissions;


  public RoleResponse(Long roleId, String name, boolean isSystem, String roleKey,
                      List<PermissionResponse> permissions) {
    this.roleId = roleId;
    this.name = name;
    this.isSystem = isSystem;
    this.roleKey = roleKey;
    this.permissions = permissions;
  }

  public void setPermissions(List<PermissionResponse> permissions) {
    this.permissions = permissions;
  }
}
