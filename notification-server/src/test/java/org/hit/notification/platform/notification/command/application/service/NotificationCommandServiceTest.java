package org.hit.notification.platform.notification.command.application.service;

import org.hit.common.event.HrNotificationEvent;
import org.hit.notification.platform.notification.command.domain.aggregate.Notification;
import org.hit.notification.platform.notification.command.domain.infrastructure.repository.NotificationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationCommandServiceTest {

    @Mock
    NotificationRepository repository;

    @InjectMocks
    NotificationCommandService service;

    @Test
    void 이벤트가_이미_처리된_경우_null을_반환한다() {
        // given
        HrNotificationEvent event = new HrNotificationEvent(
                "event-1", "Type", 1L, "title", "message", "/link"
        );
        given(repository.existsByEventId("event-1")).willReturn(true);

        // when
        Notification result = service.createFromEvent(event);

        // then
        assertThat(result).isNull();
        verify(repository, never()).save(any());
    }

    @Test
    void 새로운_이벤트면_알림을_저장한다() {
        // given
        HrNotificationEvent event = new HrNotificationEvent(
                "event-1", "Type", 1L, "title", "message", "/link"
        );
        given(repository.existsByEventId("event-1")).willReturn(false);

        // when
        Notification result = service.createFromEvent(event);

        // then
        assertThat(result).isNotNull();
        verify(repository).save(any(Notification.class));
    }

    @Test
    void 알림을_읽음처리한다() {
        // given
        Notification notification = mock(Notification.class);
        given(repository.findById(1L)).willReturn(Optional.of(notification));

        // when
        service.markAsRead(1L);

        // then
        verify(notification).markAsRead();
    }
}
