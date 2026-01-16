package org.hit.authentication.auth.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "account")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Account {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "account_id")
  private Long accId;

  @Column(name = "company_id", nullable = false)
  private Long comId;

  @Column(name = "company_code", nullable = false, length = 30, unique = true)
  private String comCode;

  @Column(name = "employee_id")
  private Long empId;

  @Column(name = "login_id", nullable = false, length = 50)
  private String loginId;

  @Column(length = 50, unique = true, nullable = false)
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

  public static Account createAccount(
      String loginId,
      String password,
      String name,
      String email) {

    return Account.builder()
        .role(Role.user)
        .loginId(loginId)
        .password(password)
        .name(name)
        .email(email)
        .status(AccountStatus.ACTIVE)
        .build();
  }

  public void updatePassword(String encode) {
    this.password = encode;
  }

  public void linkEmployee(Long empId) {
    this.empId = empId;
  }
}
