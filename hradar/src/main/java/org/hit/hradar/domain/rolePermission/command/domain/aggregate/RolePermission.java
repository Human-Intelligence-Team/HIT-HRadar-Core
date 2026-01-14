package org.hit.hradar.domain.rolePermission.command.domain.aggregate;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hit.hradar.global.dto.BaseTimeEntity;

@Entity
@Table(name = "role_permission")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RolePermission extends BaseTimeEntity{

  @EmbeddedId
  private RolePermissionId id;

  @Embeddable
  @Getter
  @NoArgsConstructor
  @AllArgsConstructor
  @EqualsAndHashCode
  public static class RolePermissionId implements java.io.Serializable {

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "perm_id", nullable = false)
    private Long permId;
  }
}
