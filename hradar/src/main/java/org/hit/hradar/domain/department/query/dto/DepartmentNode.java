package org.hit.hradar.domain.department.query.dto;

import lombok.Getter;
import lombok.Setter;
//import org.hit.hradar.domain.department.query.dto.EmployeeNode;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class DepartmentNode {
    private Long deptId;
    private String deptName;
    private Long parentDeptId;
    private List<DepartmentNode> children = new ArrayList<>();
    private List<EmployeeNode> employees = new ArrayList<>();

    private Long managerId;
    private String managerName;

    public DepartmentNode(Long deptId, String deptName, Long parentDeptId, Long managerId, String managerName) {
        this.deptId = deptId;
        this.deptName = deptName;
        this.parentDeptId = parentDeptId;
        this.managerId = managerId;
        this.managerName = managerName;
    }
}
