
package org.hit.hradar.domain.rolePermission.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "role_permission")
@Getter
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class RolePermission extends BaseTimeEntity {

  // @EmbeddedId: 엔티티에 "내장(embedded)된 PK 객체"를 사용한다는 의미
  @EmbeddedId
  private RolePermissionId id;
  private RolePermission(RolePermissionId id) {
    this.id = id;
  }

//    호출부에서 new RolePermissionId(...)를 직접 만들지 않고,
//    RolePermission.of(roleId, permId)로 간단히 생성하도록 한다.

  public static RolePermission of(Long roleId, Long permId) {
    return new RolePermission(new RolePermissionId(roleId, permId));
  }

  /**
   * 복합키 클래스.
   * -@Embeddable: 다른 엔티티에 "내장"될 수 있는 값 타입(PK)임을 선언.
   * implements Serializable: JPA 복합키 요구사항(특히 Hibernate/JPA 스펙 관례상 필요).
   * equals/hashCode 필수: 영속성 컨텍스트에서 엔티티 식별/캐싱/비교에 사용된다.
   */
  @Embeddable
  @Getter
  @NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
  @AllArgsConstructor // roleId, permId를 받는 생성자 자동 생성
  @EqualsAndHashCode  // 복합키 비교를 위한 equals/hashCode 자동 생성(매우 중요)
  public static class RolePermissionId implements java.io.Serializable {


    @Column(name = "role_id", nullable = false)
    private Long roleId;


    @Column(name = "perm_id", nullable = false)
    private Long permId;
  }
}
