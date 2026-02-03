package org.hit.hradar.domain.evaluation.command.application.service;

import org.hit.hradar.domain.evaluation.command.application.dto.request.EvaluationTypeCreateRequest;
import org.hit.hradar.domain.evaluation.command.domain.aggregate.EvaluationType;
import org.hit.hradar.domain.evaluation.command.domain.repository.EvaluationTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EvaluationTypeCommandServiceTest {

    @InjectMocks
    private EvaluationTypeCommandService service;

    @Mock
    private EvaluationTypeRepository evaluationTypeRepository;

    @Test
    @DisplayName("평가 유형 생성 - 성공")
    void create_success() {
        // given
        Long compId = 1L;
        EvaluationTypeCreateRequest request = new EvaluationTypeCreateRequest();
        setField(request, "typeName", "Upward Evaluation");

        when(evaluationTypeRepository.existsByCompanyIdAndTypeNameAndIsDeleted(compId, "Upward Evaluation", 'N'))
                .thenReturn(false);

        EvaluationType savedType = mock(EvaluationType.class);
        when(savedType.getEvalTypeId()).thenReturn(100L);
        when(evaluationTypeRepository.save(any(EvaluationType.class))).thenReturn(savedType);

        // when
        Long typeId = service.create(compId, request);

        // then
        assertThat(typeId).isEqualTo(100L);
        verify(evaluationTypeRepository).save(any(EvaluationType.class));
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
