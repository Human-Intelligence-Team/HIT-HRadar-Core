package org.hit.authentication.auth.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "user_account",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_COMPANY_LOGINID", columnNames = {"com_id", "login_id"}),
        @UniqueConstraint(name = "UK_ACCOUNT_COMPANY_EMAIL", columnNames = {"com_id", "email"})
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long accId;

  // HR 스키마 기준: com_id
  @Column(name = "com_id", nullable = false)
  private Long comId;

  // HR 스키마 기준: company_code (unique 금지)
  @Column(name = "company_code", nullable = false, length = 30)
  private String comCode;

  @Column(name = "employee_id")
  private Long empId;

  @Column(name = "login_id", nullable = false, length = 50)
  private String loginId;

  // HR 스키마 기준: email nullable 허용 + (com_id, email) 유니크
  @Column(name = "email", length = 50)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 10)
  private Role role;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 15)
  private AccountStatus status;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;

  public static Account createAccount(
      Long comId,
      String comCode,
      Long empId,
      String loginId,
      String encodedPassword,
      String name,
      String email,
      Role role
  ) {
    return Account.builder()
        .comId(comId)
        .comCode(comCode)
        .empId(empId)
        .loginId(loginId)
        .password(encodedPassword)
        .name(name)
        .email(email)
        .role(role == null ? Role.user : role)
        .status(AccountStatus.ACTIVE)
        .isDeleted('N')
        .build();
  }

  public void updatePassword(String encodedPw) {
    this.password = encodedPw;
  }

  public void linkEmployee(Long empId) {
    this.empId = empId;
  }

  public void deactivate() {
    this.status = AccountStatus.INACTIVE; // enum에 없으면 제거/수정
  }

  public void softDelete() {
    this.isDeleted = 'Y';
  }
}
