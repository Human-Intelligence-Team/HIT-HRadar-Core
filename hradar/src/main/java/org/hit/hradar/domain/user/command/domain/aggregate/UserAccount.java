package org.hit.hradar.domain.user.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount extends BaseTimeEntity {

  @Id
  @Column(name = "user_id", nullable = false)
  private Long id;

  @Column(name = "email", nullable = false, length = 255)
  private String email;

  @Column(name = "password_hash", nullable = false, length = 255)
  private String passwordHash;

  @Column(name = "role", nullable = false)
  private UserRole userRole;

  @Column(name = "is_deleted", nullable = false, length = 1)
  private Character isDeleted = 'N';

  @Enumerated(EnumType.STRING)
  @Column(name = "onboarding_status", nullable = false)
  private OnboardingStatus onboardingStatus;

}