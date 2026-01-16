package org.hit.notification.platform.notification.sse;

import lombok.RequiredArgsConstructor;

import org.hit.notification.platform.global.aop.CurrentUser;
import org.hit.notification.platform.global.dto.AuthUser;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationSseController {

    private final SseEmitterManager emitterManager;

    @GetMapping(
            value = "/subscribe",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public SseEmitter subscribe(@CurrentUser AuthUser authUser) {
        Long userId = authUser.userId(); // JWT 등으로 추출
        return emitterManager.connect(userId);
    }
}