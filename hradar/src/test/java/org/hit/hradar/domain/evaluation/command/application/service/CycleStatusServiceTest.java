package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CycleStatusServiceTest {

    @InjectMocks
    private CycleStatusService service;

    @Mock
    private CycleRepository cycleRepository;

    @Test
    @DisplayName("상태 자동 업데이트 - 종료일 경과시 CLOSED")
    void autoUpdateCycleStatus_closeExpired() {
        // given
        Cycle cycle = mock(Cycle.class);
        when(cycle.getEndDate()).thenReturn(LocalDateTime.now().minusDays(1));
        
        when(cycleRepository.findAllByStatusIn(anyList())).thenReturn(List.of(cycle));

        // when
        service.autoUpdateCycleStatus();

        // then
        verify(cycle).close();
    }
}
