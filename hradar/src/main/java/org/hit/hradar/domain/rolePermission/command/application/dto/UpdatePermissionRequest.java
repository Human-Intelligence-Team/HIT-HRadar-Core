package org.hit.hradar.domain.rolePermission.command.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePermissionRequest {

    private String name;
    private String routePath;
    private String description;

}
