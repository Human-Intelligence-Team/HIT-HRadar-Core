package org.hit.hradar.domain.department.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeNode {
    private Long empId;
    private String name;
    private String positionName;
}
