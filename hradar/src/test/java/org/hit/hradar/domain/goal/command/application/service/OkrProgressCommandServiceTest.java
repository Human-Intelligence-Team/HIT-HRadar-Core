package org.hit.hradar.domain.goal.command.application.service;

import org.hit.hradar.domain.goal.command.application.dto.request.CreateOkrProgressRequest;
import org.hit.hradar.domain.goal.command.domain.aggregate.Goal;
import org.hit.hradar.domain.goal.command.domain.aggregate.OkrKeyResult;
import org.hit.hradar.domain.goal.command.domain.aggregate.OkrProgressLog;
import org.hit.hradar.domain.goal.command.domain.repository.OkrKeyResultRepository;
import org.hit.hradar.domain.goal.command.infrastructure.OkrProgressLogJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OkrProgressCommandServiceTest {

    @InjectMocks
    private OkrProgressCommandService service;

    @Mock
    private OkrKeyResultRepository okrKeyResultRepository;

    @Mock
    private OkrProgressLogJpaRepository okrProgressLogJpaRepository;

    @Test
    @DisplayName("OKR 성과 입력 - 성공")
    void createProgress_success() {
        // given
        Long krId = 1L;
        Long actorId = 10L;
        CreateOkrProgressRequest request = new CreateOkrProgressRequest();
        setField(request, "logDate", LocalDate.now());
        setField(request, "currentProgress", 50.0);

        Goal goal = mock(Goal.class);
        when(goal.getStartDate()).thenReturn(LocalDate.now().minusDays(1));
        when(goal.getEndDate()).thenReturn(LocalDate.now().plusDays(1));

        OkrKeyResult kr = mock(OkrKeyResult.class);
        when(kr.getGoal()).thenReturn(goal);

        when(okrKeyResultRepository.findById(krId)).thenReturn(Optional.of(kr));

        // when
        service.createProgress(krId, actorId, request);

        // then
        verify(okrProgressLogJpaRepository).save(any(OkrProgressLog.class));
    }

    private void setField(Object target, String fieldName, Object value) {
        try {
            var field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
