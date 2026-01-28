package org.hit.hradar.domain.rolePermission.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RoleListRequest {

  // 선택: 키워드 검색
  private String keyword;

  // 선택: 시스템 역할만 / 커스텀만
  // null이면 전체, true면 시스템만, false면 커스텀만
  private Boolean isSystem;

  public RoleListRequest(String keyword, Boolean systemOnly) {
    this.keyword = keyword;
    this.isSystem = isSystem;
  }
}
