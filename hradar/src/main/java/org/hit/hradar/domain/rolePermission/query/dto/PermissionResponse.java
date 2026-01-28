package org.hit.hradar.domain.rolePermission.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PermissionResponse {

  private Long permId;
  private Long parentPermId;
  private String permKey;
  private String name;
  private String permType;
  private String routePath;
  private String description;

  public PermissionResponse(Long permId, Long parentPermId, String permKey, String name,
                            String permType, String routePath, String description) {
    this.permId = permId;
    this.parentPermId = parentPermId;
    this.permKey = permKey;
    this.name = name;
    this.permType = permType;
    this.routePath = routePath;
    this.description = description;
  }
}
