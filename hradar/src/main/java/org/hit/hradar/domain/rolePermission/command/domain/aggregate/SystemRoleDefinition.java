package org.hit.hradar.domain.rolePermission.command.domain.aggregate;

import java.util.List;
import lombok.Getter;

@Getter
public enum SystemRoleDefinition {
    OWNER("OWNER", "최고관리자", List.of(
            "USER_MANAGE", "DEPT_MANAGE", "COMPANY_MANAGE", "ROLE_MANAGE",
            "NOTICE_MANAGE", "MY_PROFILE_READ", "MY_PROFILE_UPDATE")),
    EMPLOYEE("EMPLOYEE", "사원", List.of(
            "MY_PROFILE_READ", "MY_PROFILE_UPDATE", "NOTICE_READ", "POLICY_READ")),
    HRTEAM("HRTEAM", "인사팀", List.of(
            "USER_MANAGE", "DEPT_MANAGE", "NOTICE_MANAGE", "MY_PROFILE_READ", "MY_PROFILE_UPDATE")),
    TEAMLEADER("TEAMLEADER", "팀장", List.of(
            "DEPT_READ", "MY_PROFILE_READ", "MY_PROFILE_UPDATE", "NOTICE_READ"));

    private final String roleKey;
    private final String name;
    private final List<String> defaultPermKeys;

    SystemRoleDefinition(String roleKey, String name, List<String> defaultPermKeys) {
        this.roleKey = roleKey;
        this.name = name;
        this.defaultPermKeys = defaultPermKeys;
    }
}
