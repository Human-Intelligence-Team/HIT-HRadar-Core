package org.hit.hradar.domain.rolePermission.command.application.service;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Role;
import org.hit.hradar.domain.rolePermission.command.infrastructure.RoleJpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultRoleCommandService {

  private final RoleJpaRepository roleRepository;

  private static final List<SystemRoleSeed> DEFAULT_ROLES = List.of(
      new SystemRoleSeed("OWNER", "최고관리자"),
      new SystemRoleSeed("HRTEAM", "인사팀"),
      new SystemRoleSeed("TEAMLEADER", "팀장"),
      new SystemRoleSeed("EMPLOYEE", "사원")
  );

  @Transactional
  public List<Role> ensureDefaults(Long comId) {

    List<Role> toCreate = new ArrayList<>();

    for (SystemRoleSeed seed : DEFAULT_ROLES) {
      boolean exists = roleRepository.findByComIdAndRoleKey(comId, seed.roleKey()).isPresent();
      if (!exists) {
        toCreate.add(Role.createSystemRole(comId, seed.roleKey(), seed.name()));
      }
    }

    if (!toCreate.isEmpty()) {
      roleRepository.saveAll(toCreate);
    }

    return roleRepository.findAllByComId(comId);
  }

  private static class SystemRoleSeed {
    private final String roleKey;
    private final String name;

    private SystemRoleSeed(String roleKey, String name) {
      this.roleKey = roleKey;
      this.name = name;
    }

    public String roleKey() { return roleKey; }
    public String name() { return name; }
  }
}
