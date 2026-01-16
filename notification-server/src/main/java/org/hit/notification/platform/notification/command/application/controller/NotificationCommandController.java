package org.hit.notification.platform.notification.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.notification.platform.global.aop.CurrentUser;
import org.hit.notification.platform.global.dto.ApiResponse;
import org.hit.notification.platform.global.dto.AuthUser;
import org.hit.notification.platform.notification.command.application.service.NotificationCommandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationCommandController {

    private final NotificationCommandService commandService;

    @PostMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> read(@PathVariable Long id) {
        commandService.markAsRead(id);
        return ResponseEntity.ok()
                .body(ApiResponse.success(null));
    }

    @PostMapping("/read-all")
    public ResponseEntity<ApiResponse<Void>> readAll(@CurrentUser AuthUser authUser) {
        commandService.markAsReadAll(authUser.userId());
        return ResponseEntity.ok()
                .body(ApiResponse.success(null));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        commandService.delete(id);
        return ResponseEntity.ok()
                .body(ApiResponse.success(null));
    }
}