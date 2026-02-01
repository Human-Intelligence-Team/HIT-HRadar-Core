package org.hit.hradar.domain.rolePermission.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEmployeeRolesRequest {
    private List<Long> roleIds;
}
