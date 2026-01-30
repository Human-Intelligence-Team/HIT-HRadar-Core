package org.hit.hradar.domain.user.command.domain.aggregate;

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
@Table(name = "user_account", uniqueConstraints = {
    @UniqueConstraint(name = "UK_COMPANY_LOGINID", columnNames = {"com_id", "login_id"}),
    @UniqueConstraint(name = "UK_ACCOUNT_COMPANY_EMAIL", columnNames = {"com_id", "email"}) })
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Account {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "account_id")
   private Long accId;

   @Column(name = "com_id", nullable = false)
   private Long comId;

   @Column(name = "company_code", nullable = false, length = 30)
  private String comCode;

   @Column(name = "employee_id")
   private Long empId;

   @Column(name = "login_id", nullable = false, length = 50)
   private String loginId;

   @Column(name = "email", length = 50)
   private String email;

   @Column(name = "password", nullable = false, length = 150)
   private String password;

   @Column(name = "name", nullable = false, length = 100)
   private String name;

   @Enumerated(EnumType.STRING)
   @Column(name = "role", nullable = false, length = 10)
   private UserRole userRole;

   @Enumerated(EnumType.STRING)
   @Column(name = "status", nullable = false, length = 15)
   private AccountStatus status;

   @Column(name = "is_deleted", nullable= false ,
       columnDefinition = "CHAR(1) DEFAULT 'N'")
   private Character isDeleted;

   @Builder public Account(Long accId, Long comId,String comCode, Long empId,
       String loginId, String email, String password, String name, UserRole userRole,
       Character isDeleted, AccountStatus status)
   { this.accId = accId;
     this.comId = comId;
     this.comCode = comCode;
     this.empId = empId;
     this.loginId = loginId;
     this.email = email;
     this.password = password;
     this.name = name;
     this.userRole = userRole;
     this.status = AccountStatus.ACTIVE;
     this.isDeleted = 'N'; }

  public void updateLoginInfo(String loginId, String name, String email)
  { this.loginId = loginId;
    this.name = name;
    this.email = email; }

  public void updatePassword(String newPassword)
  { this.password = newPassword; }

  public void updateAccountStatus(AccountStatus newStatus)
  { this.status = newStatus; }

  public void isDeleted()
  { this.isDeleted = 'Y';
    this.status = AccountStatus.INACTIVE; }

 }