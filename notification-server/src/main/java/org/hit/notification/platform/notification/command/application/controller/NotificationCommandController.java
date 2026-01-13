package org.hit.notification.platform.notification.command.application.controller;

import lombok.RequiredArgsConstructor;
import org.hit.notification.platform.notification.command.application.service.NotificationCommandService;
import org.hit.notification.platform.notification.query.service.NotificationQueryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationCommandController {

    private final NotificationCommandService commandService;

    @PostMapping("/{id}/read")
    public void read(@PathVariable Long id) {
        commandService.markAsRead(id);
    }

    // 3️⃣ 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        commandService.delete(id);
    }
}