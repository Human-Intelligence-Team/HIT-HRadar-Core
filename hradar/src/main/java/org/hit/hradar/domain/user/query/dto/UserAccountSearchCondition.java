package org.hit.hradar.domain.user.query.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAccountSearchCondition {
  private String keyword; // loginId/name/email 검색
  private String status; // ACTIVE/INACTIVE...
  private String role; // ADMIN/USER...
  private Boolean includeDeleted; // true면 삭제 포함

  private Long comId; // 회사 필터 (플랫폼 관리자용)
  private Long deptId; // 부서 필터
}