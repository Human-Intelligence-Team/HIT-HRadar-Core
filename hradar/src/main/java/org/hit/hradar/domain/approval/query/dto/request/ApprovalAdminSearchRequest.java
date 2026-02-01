package org.hit.hradar.domain.approval.query.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.query.SortDirection;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalAdminSearchRequest {

  private String docType;        // 문서 유형
  private String status;         // 결재 상태
  private Long deptId;           // 부서 ID
  private Long empId;            // 사원 ID
  private SortDirection sort;    // ASC / DESC
}
