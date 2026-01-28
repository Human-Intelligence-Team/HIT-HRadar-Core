package org.hit.hradar.domain.me.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MeResponse {

  // token 기반
  private Long userId;
  private String role;

  // 회사
  private Long comId;
  private String companyCode;
  private String companyName;

  // 사원
  private Long empId;
  private String employeeName;
  private String employeeNo;
  private String email;

  // 부서(없을 수 있음)
  private Long deptId;
  private String deptName;

  // 직위(없을 수 있음)
  private Long positionId;
  private String positionName;

  // (선택) 생성/수정일 같은거 필요하면 확장
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
