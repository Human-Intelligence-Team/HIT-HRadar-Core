package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.application.dto.request.CycleCreateRequestDto;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.Cycle;
import org.hit.hradar.domain.evaluation.command.domain.repository.CycleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CycleCommandServiceTest {

    @InjectMocks
    private CycleCommandService service;

    @Mock
    private CycleRepository cycleRepository;

    @Test
    @DisplayName("평가 회차 생성 - 성공")
    void createCycle_success() {
        // given
        Long companyId = 1L;
        Long empId = 100L;
        CycleCreateRequestDto request = new CycleCreateRequestDto();
        setField(request, "year", "2024");
        setField(request, "quarter", org.hit.hradar.domain.competencyReport.command.domain.aggregate.Quarter.Q1);
        setField(request, "cycleName", "1Q Evaluation");
        setField(request, "startDate", LocalDateTime.now().minusDays(1));
        setField(request, "endDate", LocalDateTime.now().plusDays(10));

        // when
        service.createCycle(companyId, request, empId);

        // then
        verify(cycleRepository).save(any(Cycle.class));
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
