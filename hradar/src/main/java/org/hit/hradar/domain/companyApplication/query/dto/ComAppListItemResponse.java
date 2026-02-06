package org.hit.hradar.domain.companyApplication.query.dto;

import java.time.LocalDateTime;
import lombok.*;

/**
 * 회사 신청 목록 아이템 응답 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComAppListItemResponse {

  // 신청서 PK
  private Long appId;

  // 회사 정보
  private String companyName;
  private String companyCode; // 승인 후 생성된 회사 코드
  private String bizNo;
  private String comTel;
  private String address;

  // 신청자(회사 생성자) 정보
  private String createrName;
  private String createrEmail;
  private String createrLoginId;

  // 신청 상태 및 처리 메타
  private String status;
  private LocalDateTime reviewedAt;
  private Long reviewedBy;
  private String rejectReason;

}
