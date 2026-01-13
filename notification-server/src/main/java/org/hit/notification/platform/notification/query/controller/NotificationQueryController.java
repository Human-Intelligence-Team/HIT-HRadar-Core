package org.hit.notification.platform.notification.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.notification.platform.notification.command.application.dto.NotificationResponse;
import org.hit.notification.platform.notification.query.service.NotificationQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationQueryController {

    private final NotificationQueryService queryService;

    @GetMapping()
    public List<NotificationResponse> getMyNotifications() {
        Long userId = 1L; // 지금은 임시
        return queryService.findByUserId(userId);
    }
}