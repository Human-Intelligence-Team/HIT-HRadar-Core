package org.hit.hradar.domain.rolePermission.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.rolePermission.command.domain.aggregate.Permission;
import org.hit.hradar.domain.rolePermission.command.infrastructure.PermissionJpaRepository;
import org.hit.hradar.domain.rolePermission.query.dto.PermissionResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PermissionQueryService {

    private final PermissionJpaRepository permissionRepository;

    public List<PermissionResponse> getAllPermissions() {
        List<Permission> permissions = permissionRepository.findAll();
        // Assuming we want to filter out deleted ones for list view, though admin might
        // vary.
        // Logic usually filters soft-deleted items unless specialized admin view.
        return permissions.stream()
                .filter(p -> p.getIsDeleted() == 'N')
                .map(p -> new PermissionResponse(
                        p.getPermId(),
                        p.getParentPermId(),
                        p.getPermKey(),
                        p.getName(),
                        null, // permType is not in Entity currently
                        p.getRoutePath(),
                        p.getDescription()))
                .collect(Collectors.toList());
    }
}
