package org.hit.hradar.domain.rolePermission.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.*;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(
    name = "role",
    uniqueConstraints = {
        @UniqueConstraint(name = "UK_ROLE_COM_NAME", columnNames = {"com_id", "name"}),
        @UniqueConstraint(name = "UK_ROLE_COM_ROLE_KEY", columnNames = {"com_id", "role_key"})
    },
    indexes = {
        @Index(name = "IDX_ROLE_COM_ID", columnList = "com_id")
    }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Role extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "role_id", nullable = false)
  private Long roleId;

  @Column(name = "com_id", nullable = false)
  private Long comId;

  /**
   * 시스템 기본 역할 여부
   * - true  : 회사 생성 시 자동 생성되는 기본 역할(4개)
   * - false : 회사 커스텀 역할
   */
  @Column(name = "is_system", nullable = false)
  @Builder.Default
  private boolean isSystem = false;

  /**
   * 기본 역할 식별키 (커스텀 역할은 null 권장)
   * 예: OWNER, HRTEAM, TEAMLEADER, EMPLOYEE
   */
  @Column(name = "role_key", length = 100)
  private String roleKey;

  @Column(name = "name", nullable = false, length = 255)
  private String name;

  @Column(name = "is_deleted", nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N'")
  private Character isDeleted;



  public static Role createSystemRole(Long comId, String roleKey, String name) {
    return Role.builder()
        .comId(comId)
        .isSystem(true)
        .roleKey(roleKey)
        .name(name)
        .isDeleted('N')
        .build();
  }

  public static Role createCustomRole(Long comId, String name) {
    return Role.builder()
        .comId(comId)
        .isSystem(false)
        .roleKey(null)  // 커스텀 역할은 null
        .name(name)
        .isDeleted('N')
        .build();
  }
  public boolean isDeleted() {
    return this.isDeleted != null && this.isDeleted == 'Y';
  }

  public void markDeleted() {
    this.isDeleted = 'Y';
  }

  public void changeName(String name) {
    this.name = name;
  }

}
