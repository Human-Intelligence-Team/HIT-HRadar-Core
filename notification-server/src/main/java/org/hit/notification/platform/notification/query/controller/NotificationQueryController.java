package org.hit.notification.platform.notification.query.controller;

import lombok.RequiredArgsConstructor;
import org.hit.notification.platform.global.aop.CurrentUser;
import org.hit.notification.platform.global.dto.AuthUser;
import org.hit.notification.platform.notification.command.application.dto.NotificationResponse;
import org.hit.notification.platform.notification.query.service.NotificationQueryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationQueryController {

    private final NotificationQueryService queryService;

    @GetMapping()
    public List<NotificationResponse> getMyNotifications(@CurrentUser AuthUser authUser) {
        return queryService.findByUserId(authUser.userId());
    }
}