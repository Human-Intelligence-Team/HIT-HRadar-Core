package org.hit.notification.platform.notification.query.service;

import lombok.RequiredArgsConstructor;
import org.hit.notification.platform.notification.command.application.dto.NotificationResponse;
import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.hit.notification.platform.notification.command.domain.infrastructure.repository.NotificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationQueryService {

    private final NotificationRepository repository;

    public List<NotificationResponse> findByUserId(Long userId) {
        return repository.findByUserId(userId)
                .stream()
                // 최신순 정렬 (DB에서 안 했을 경우 대비)
                .sorted(Comparator.comparing(Notification::getCreatedAt).reversed())
                // DTO 변환
                .map(NotificationResponse::from)
                .toList();
    }
}
