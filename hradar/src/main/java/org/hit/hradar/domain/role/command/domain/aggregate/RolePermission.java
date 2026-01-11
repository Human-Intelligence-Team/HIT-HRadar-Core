package org.hit.hradar.domain.role.command.domain.aggregate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Embeddable
public class RolePermission implements Serializable {

  @Column(name = "perm_id", nullable = false)
  private Long permId;

  @Column(name = "role_id", nullable = false)
  private Long roleId;
}