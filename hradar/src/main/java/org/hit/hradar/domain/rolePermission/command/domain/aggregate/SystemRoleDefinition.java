package org.hit.hradar.domain.rolePermission.command.domain.aggregate;

import java.util.List;
import lombok.Getter;

@Getter
public enum SystemRoleDefinition {
    ADMIN("ADMIN", "최고 관리자", List.of(
        "POLICY_READ", "NOTICE_READ", "NOTICE_MANAGE",
        "MY_PROFILE", "MY_DEPT", "ORG_CHART",
        "DEPT_MANAGE", "EMP_MANAGE", "EMP_HISTORY",
        "POS_read", "POS_MANAGE", "COM_MANAGE_MY",
        "ROLE_MANAGE", "GOAL_LIST", "GOAL_CREATE",
        "GOAL_TEAM_LIST", "GOAL_DASH_HR", "REPORT_ME",
        "REPORT_DEPT", "REPORT_ALL", "CONTENT_ALL",
        "TAG_MANAGE", "SALARY_BASIC_ME", "SALARY_BASIC_ALL",
        "SALARY_COMP_ALL", "SALARY_DASH", "GRADE_MY",
        "GRADE_LIST_DEPT", "GRADE_LIST_HR", "GRADE_ASSIGN",
        "GRADE_APPROVE", "CYCLE_MANAGE_HR","GRADE_SETTING", "GRADE_OBJECTION",
        "EVAL_EXEC", "EVAL_RESULT_MY", "EVAL_RESULT_HR",
        "EVAL_TYPE", "EVAL_FORM", "EVAL_ASSIGN",
        "EVAL_STATUS", "DASH_MY", "DASH_HR",
        "APPR_CREATE", "APPR_MY", "APPR_ALL",
        "APPR_ADMIN", "APPR_TYPE", "ATT_COMMUTE",
        "ATT_DEPT", "ATT_CAL", "ATT_IP",
        "LEAVE_MY", "LEAVE_DEPT", "LEAVE_POLICY"

    )),
    EMPLOYEE("EMPLOYEE", "일반 사원", List.of(
        "POLICY_READ",
        "NOTICE_READ", "MY_PROFILE", "MY_DEPT", "DEPT_LIST", "ORG_CHART", "EMP_LIST_READ",
        "POS_read", "COM_MY", "GOAL_LIST", "REPORT_ME", "CONTENT_ALL", "SALARY_BASIC_ME",
        "GRADE_MY", "GRADE_OBJECTION", "EVAL_EXEC", "EVAL_RESULT_MY", "DASH_MY",
        "APPR_CREATE", "APPR_MY", "ATT_COMMUTE", "LEAVE_MY"
    )),
    HR_ADMIN("HR_ADMIN", "인사 관리자", List.of(
        "POLICY_READ", "NOTICE_READ", "NOTICE_MANAGE",
        "MY_PROFILE", "MY_DEPT", "ORG_CHART",
        "DEPT_MANAGE", "EMP_MANAGE", "EMP_HISTORY",
        "POS_read", "POS_MANAGE", "COM_MANAGE_MY",
        "ROLE_MANAGE", "GOAL_LIST", "GOAL_CREATE",
        "GOAL_TEAM_LIST", "GOAL_DASH_HR", "REPORT_ME",
        "REPORT_DEPT", "REPORT_ALL", "CONTENT_ALL",
        "TAG_MANAGE", "SALARY_BASIC_ME", "SALARY_BASIC_ALL",
        "SALARY_COMP_ALL", "SALARY_DASH", "GRADE_MY",
        "GRADE_LIST_DEPT", "GRADE_LIST_HR", "GRADE_ASSIGN",
        "GRADE_APPROVE", "CYCLE_MANAGE_HR", "GRADE_SETTING", "GRADE_OBJECTION",
        "EVAL_EXEC", "EVAL_RESULT_MY", "EVAL_RESULT_HR",
        "EVAL_TYPE", "EVAL_FORM", "EVAL_ASSIGN",
        "EVAL_STATUS", "DASH_MY", "DASH_HR",
        "APPR_CREATE", "APPR_MY", "APPR_ALL",
        "APPR_ADMIN", "APPR_TYPE", "ATT_COMMUTE",
        "ATT_DEPT", "ATT_CAL", "ATT_IP",
        "LEAVE_MY", "LEAVE_DEPT", "LEAVE_POLICY"

    )),
    TEAM_LEADER("TEAM_LEADER", "부서장", List.of(
        "POLICY_READ", "NOTICE_READ", "MY_PROFILE",
        "MY_DEPT", "DEPT_LIST", "ORG_CHART",
        "EMP_LIST_READ", "POS_read", "COM_MY",
        "GOAL_LIST", "REPORT_ME", "CONTENT_ALL",
        "SALARY_BASIC_ME", "GRADE_MY", "GRADE_OBJECTION",
        "EVAL_EXEC", "EVAL_RESULT_MY", "DASH_MY",
        "APPR_CREATE", "APPR_MY", "ATT_COMMUTE",
        "LEAVE_MY", "GOAL_TEAM_LIST", "GOAL_CREATE",
        "REPORT_DEPT", "GRADE_LIST_DEPT", "GRADE_ASSIGN",
        "ATT_DEPT", "ATT_CAL", "LEAVE_DEPT"

    ));

    private final String roleKey;
    private final String name;
    private final List<String> defaultPermKeys;

    SystemRoleDefinition(String roleKey, String name, List<String> defaultPermKeys) {
        this.roleKey = roleKey;
        this.name = name;
        this.defaultPermKeys = defaultPermKeys;
    }
}
