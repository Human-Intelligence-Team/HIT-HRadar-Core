package org.hit.hradar.domain.rolePermission.query.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleAssignedMember {
    private Long empId;
    private String name;
    private String departmentName;
    private String positionName;
    private String image;
}
