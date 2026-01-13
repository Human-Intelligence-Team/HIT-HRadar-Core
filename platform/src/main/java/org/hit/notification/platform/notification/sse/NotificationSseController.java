package org.hit.notification.platform.notification.sse;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationSseController {

    private final SseEmitterManager emitterManager;

    @GetMapping("/subscribe")
    public SseEmitter subscribe() {
        Long userId = getCurrentUserId(); // JWT 등으로 추출
        return emitterManager.connect(userId);
    }

    private Long getCurrentUserId() {
        // TODO: SecurityContext에서 추출
        return 1L;
    }
}