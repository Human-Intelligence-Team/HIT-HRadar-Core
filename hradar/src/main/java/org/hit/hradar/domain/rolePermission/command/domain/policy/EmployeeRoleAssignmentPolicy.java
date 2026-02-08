package org.hit.hradar.domain.rolePermission.command.domain.policy;

import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class EmployeeRoleAssignmentPolicy {

  public enum Scenario {
    FIRST_EMPLOYEE, // 회사 승인 직후 첫 사원
    NORMAL_EMPLOYEE // 이후 일반 사원
  }

  public List<String> roleKeysToAssign(Scenario scenario) {
    return switch (scenario) {
      case FIRST_EMPLOYEE -> List.of("ADMIN", "EMPLOYEE");
      case NORMAL_EMPLOYEE -> List.of("EMPLOYEE");
    };
  }
}
