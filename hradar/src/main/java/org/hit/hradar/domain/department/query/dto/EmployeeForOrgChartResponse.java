package org.hit.hradar.domain.department.query.dto;

import lombok.Getter;

@Getter
public class EmployeeForOrgChartResponse {
    private Long empId;
    private Long deptId;
    private String name;
    private String positionName;
}
