package org.hit.hradar.domain.department.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hit.hradar.global.dto.BaseTimeEntity;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentResponse extends BaseTimeEntity {
    private Long deptId;
    private Long companyId;
    private Long parentDeptId;
    private Long managerEmpId;
    private String deptName;
    private String deptPhone;

}
