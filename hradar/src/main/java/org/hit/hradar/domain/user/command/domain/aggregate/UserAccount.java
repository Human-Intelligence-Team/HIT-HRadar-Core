package org.hit.hradar.domain.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;

import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class UserAccount extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "Account_id")
  private Long accId;

  @Column(name = "company_code", nullable = false, length = 30, unique = true)
  private String comCode;

  @Column(name = "employee_id")
  private Long empId;

  @Column(name = "login_id", nullable = false, length = 50, unique = true)
  private String loginId;

  @Column(length = 50, unique = true, nullable = false)
  private String email;

  @Column(name = "password", nullable = false, length = 255)
  private String password;

  @Column(name = "name", nullable = false, length = 100)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(name = "user_role", nullable = false, length = 10)
  private UserRole userRole;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false, length = 15)
  private AccountStatus status;

  /*@Column(name = "created_by", nullable = false, length = 50)
    private String createdBy;

    @Column(name = "updated_by", length = 50)
    private String updatedBy;*/


  public static UserAccount createUserAccount(
      String loginId,
      String password,
      String name,
      String email) {

    return UserAccount.builder()
        .userRole(UserRole.user)
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
