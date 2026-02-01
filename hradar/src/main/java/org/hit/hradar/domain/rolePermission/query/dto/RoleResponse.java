package org.hit.hradar.domain.rolePermission.query.dto;

import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleResponse {

  private Long roleId;
  private String name;
  private Character isSystem;
  private String roleKey;

  private List<PermissionResponse> permissions;
  private List<RoleAssignedMember> assignedUsers;

  public RoleResponse(Long roleId, String name, Character isSystem, String roleKey,
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

  public void setAssignedUsers(List<RoleAssignedMember> assignedUsers) {
    this.assignedUsers = assignedUsers;
  }

  public int getUserCount() {
    return assignedUsers != null ? assignedUsers.size() : 0;
  }
}
