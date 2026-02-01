package org.hit.hradar.domain.rolePermission.command.infrastructure;

import java.util.List;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionJpaRepository extends JpaRepository<Permission, Long> {
  List<Permission> findAllByPermIdIn(List<Long> permIds);

  boolean existsByPermKeyAndIsDeleted(String permKey, Character isDeleted);

  java.util.Optional<Permission> findByPermKey(String permKey);
}