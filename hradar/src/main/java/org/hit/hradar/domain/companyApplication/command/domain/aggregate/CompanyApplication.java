
package org.hit.hradar.domain.companyApplication.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.domain.companyApplication.CompanyApplicationErrorCode;
import org.hit.hradar.global.exception.BusinessException;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "company_application")
public class CompanyApplication  {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "application_id", nullable = false)
  private Long appId;

  @Column(name = "company_name", nullable = false, length = 200)
  private String companyName;

  @Column(name = "biz_no", nullable = false, length = 30)
  private String bizNo;

  @Column(name = "company_telephone", nullable = false, length = 30)
  private String comTel;

  @Column(name = "address", nullable = false, length = 255)
  private String address;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 15)
  private CompanyApplicationStatus status;

  // 회사 생성자의 이름, 이메일, 로그인id(원하는 바)
  @Column(name = "creater_name", length = 50, nullable = false)
  private String name;

  @Column(name = "creater_email", length = 100, nullable = false)
  private String email;

  @Column(name = "creater_login_id", length = 100, nullable = false)
  private String loginId;

  @Column(name = "reject_reason", length = 500)
  private String rejectReason;

  @Column(name = "reviewed_at")
  private LocalDateTime reviewedAt;


  @Column(name = "reviewed_by")
  private Long reviewedBy; // 승인/반려 처리한 플랫폼 관리자 userId

  /**
   * 회사 신청 승인 처리
   * @param reviewerUserId 승인 처리한 플랫폼 관리자 userId
   */
  public void approveNow(Long reviewerUserId) {
    // 현재 상태가 SUBMITTED가 아니면 승인 불가
    if (this.status != CompanyApplicationStatus.SUBMITTED) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
    }
    // 승인자는 필수(누가 처리했는지 감사 로그 성격)
    if (reviewerUserId == null) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_REVIEWER);
    }

    this.status = CompanyApplicationStatus.APPROVED;// 상태 변경
    this.reviewedAt = LocalDateTime.now(); // 처리 일시/처리자 기록
    this.reviewedBy = reviewerUserId;
    this.rejectReason = null;
  }

  /**
   * 회사 신청 반려 처리
   * @param reason 반려 사유(필수)
   * @param reviewerUserId 반려 처리한 플랫폼 관리자 userId(필수)
   */
  public void rejectNow(String reason, Long reviewerUserId) {
    // 현재 상태가 SUBMITTED가 아니면 반려 불가
    if (this.status != CompanyApplicationStatus.SUBMITTED) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
    }
    // 반려사유는 비어있으면 안됨
    if (reason == null || reason.trim().isEmpty()) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_REJECT_REASON);
    }
    // 반려자도 필수
    if (reviewerUserId == null) {
      throw new BusinessException(CompanyApplicationErrorCode.INVALID_REVIEWER);
    }

    this.status = CompanyApplicationStatus.REJECTED;     // 상태 변경
    this.rejectReason = reason.trim();                   // 반려 메타데이터 기록
    this.reviewedAt = LocalDateTime.now();
    this.reviewedBy = reviewerUserId;
  }
}
