package org.hit.hradar.domain.companyApplication.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.domain.companyApplication.CompanyApplicationErrorCode;
import org.hit.hradar.global.dto.BaseTimeEntity;

import java.time.LocalDateTime;
import org.hit.hradar.global.exception.BusinessException;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "company_application")
public class CompanyApplication extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "application_id", nullable = false)
  private Long appId;

  @Column(name = "company_name", nullable = false, length = 200)
  private String comName;

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
  @Column(name = "company_ctor_name", length = 50, nullable = false)
  private String name;

  @Column(name = "company_ctor_email", length = 30, nullable = false)
  private String email;

  @Column(name = "company_ctor_login_id", length = 100, nullable = false)
  private String loginId;

  @Column(name = "reviewed_at")
  private LocalDateTime reviewedAt;

  @Column(name = "reject_reason", length = 500)
  private String rejectReason;


    public void approveNow() {
      if (this.status != CompanyApplicationStatus.SUBMITTED) {
        throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
      }
      this.status = CompanyApplicationStatus.APPROVED;
      this.reviewedAt = java.time.LocalDateTime.now();
    }

    public void rejectNow(String reason) {
      if (this.status != CompanyApplicationStatus.SUBMITTED) {
        throw new BusinessException(CompanyApplicationErrorCode.INVALID_STATUS);
      }
      this.status = CompanyApplicationStatus.REJECTED;
      this.rejectReason = reason;
      this.reviewedAt = java.time.LocalDateTime.now();
    }

  }

