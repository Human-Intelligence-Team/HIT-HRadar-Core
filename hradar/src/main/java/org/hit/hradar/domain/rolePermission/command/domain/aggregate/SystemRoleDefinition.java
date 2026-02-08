package org.hit.hradar.domain.rolePermission.command.domain.aggregate;

import java.util.List;
import lombok.Getter;

@Getter
public enum SystemRoleDefinition {
    ADMIN("ADMIN", "최고 관리자", List.of(
            "POLICY_READ", "NOTICE_READ", "NOTICE_MANAGE",
            "MY_PROFILE", "MY_DEPT",
            "DEPT_LIST", "ORG_CHART", "DEPT_MANAGE",
            "EMP_MANAGE", "EMP_LIST_READ",
            "POS_MANAGE", "POS_read",
            "EMP_APPOINT", "EMP_HISTORY",
            "COM_MY", "COM_MANAGE_MY", "ROLE_MANAGE", "COM_MANAGE_ALL",
            "GOAL_LIST", "GOAL_DASH_HR", "GOAL_TEAM_LIST", "GOAL_CREATE",
            "REPORT_ALL", "REPORT_DEPT", "REPORT_ME",
            "SALARY_DASH", "SALARY_BASIC_ALL", "SALARY_BASIC_ME", "SALARY_COMP_ALL",
            "CONTENT_ALL", "TAG_MANAGE",
            "CYCLE_MANAGE", "CYCLE_MANAGE_HR",
            "GRADE_SETTING", "GRADE_LIST_DEPT", "GRADE_LIST_HR", "GRADE_ASSIGN", "GRADE_APPROVE", "GRADE_MY", "GRADE_OBJECTION",
            "EVAL_TYPE", "EVAL_FORM", "EVAL_ASSIGN", "EVAL_STATUS", "EVAL_EXEC", "EVAL_RESULT_HR", "EVAL_RESULT_MY",
            "DASH_MY", "DASH_HR",
            "APPR_TYPE", "APPR_CREATE", "APPR_MY", "APPR_ALL", "APPR_ADMIN",
            "ATT_COMMUTE", "ATT_IP", "ATT_DEPT", "ATT_CAL",
            "LEAVE_MY", "LEAVE_POLICY", "LEAVE_DEPT"
    )),
    EMPLOYEE("EMPLOYEE", "일반 사원", List.of(
            "MY_PROFILE", "MY_DEPT", "POLICY_READ", "NOTICE_READ",
            "ATT_COMMUTE", "LEAVE_MY", "APPR_MY", "APPR_CREATE",
            "DASH_MY", "SALARY_BASIC_ME", "EVAL_EXEC", "EVAL_RESULT_MY",
            "REPORT_ME", "CONTENT_ALL", "GRADE_MY", "GOAL_LIST"
    )),
    HR_ADMIN("HR_ADMIN", "인사 관리자", List.of(
            "MY_PROFILE", "MY_DEPT", "POLICY_READ", "NOTICE_READ", "NOTICE_MANAGE",
            "DEPT_LIST", "ORG_CHART", "DEPT_MANAGE",
            "EMP_MANAGE", "EMP_LIST_READ", "POS_MANAGE", "POS_read", "EMP_APPOINT", "EMP_HISTORY",
            "ATT_COMMUTE", "ATT_IP", "ATT_DEPT", "ATT_CAL",
            "LEAVE_MY", "LEAVE_POLICY", "LEAVE_DEPT",
            "GOAL_DASH_HR", "SALARY_BASIC_ALL", "SALARY_DASH", "SALARY_COMP_ALL",
            "EVAL_TYPE", "EVAL_FORM", "EVAL_ASSIGN", "EVAL_STATUS", "EVAL_RESULT_HR",
            "GRADE_SETTING", "GRADE_LIST_HR", "GRADE_APPROVE",
            "DASH_HR"
    )),
    TEAM_LEADER("TEAM_LEADER", "부서장", List.of(
            "MY_PROFILE", "MY_DEPT", "POLICY_READ", "NOTICE_READ",
            "ATT_COMMUTE", "LEAVE_MY", "APPR_MY", "APPR_CREATE",
            "GOAL_TEAM_LIST", "GRADE_LIST_DEPT", "GRADE_ASSIGN", "GRADE_OBJECTION",
            "REPORT_DEPT"
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
