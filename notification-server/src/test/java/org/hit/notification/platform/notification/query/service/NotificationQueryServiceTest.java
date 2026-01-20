package org.hit.notification.platform.notification.query.service;

import org.hit.notification.platform.notification.command.application.dto.NotificationResponse;
import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.hit.notification.platform.notification.command.domain.infrastructure.repository.NotificationRepository;
import org.hit.notification.platform.notification.fixture.NotificationFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class NotificationQueryServiceTest {

    @Mock
    NotificationRepository repository;

    @InjectMocks
    NotificationQueryService service;

    @Test
    void 알림을_최신순으로_조회한다() {
        // given
        Notification old = NotificationFixture.unread(1L, LocalDateTime.now().minusDays(1));
        Notification latest = NotificationFixture.unread(2L, LocalDateTime.now());

        given(repository.findByUserId(1L))
                .willReturn(List.of(old, latest));

        // when
        List<NotificationResponse> result = service.findByUserId(1L);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.getFirst().getCreatedAt())
                .isAfter(result.get(1).getCreatedAt());

    }
}
