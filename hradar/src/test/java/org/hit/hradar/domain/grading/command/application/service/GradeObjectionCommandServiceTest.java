package org.hit.hradar.domain.grading.command.application.service;

import org.hit.hradar.domain.grading.command.aplication.dto.request.RegisterGradeObjectionRequestDto;
import org.hit.hradar.domain.grading.command.aplication.sevice.GradeObjectionCommandService;
import org.hit.hradar.domain.grading.command.domain.aggregate.GradeObjection;
import org.hit.hradar.domain.grading.command.domain.aggregate.IndividualGrade;
import org.hit.hradar.domain.grading.command.domain.repository.GradeObjectionRepository;
import org.hit.hradar.domain.grading.command.domain.repository.IndividualGradeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GradeObjectionCommandServiceTest {

    @InjectMocks
    private GradeObjectionCommandService service;

    @Mock
    private GradeObjectionRepository gradeObjectionRepository;

    @Mock
    private IndividualGradeRepository individualGradeRepository;

    @Test
    @DisplayName("이의 제기 등록 - 성공")
    void registerObjection_success() {
        // given
        RegisterGradeObjectionRequestDto request = new RegisterGradeObjectionRequestDto();
        setField(request, "individualGradeId", 100L);
        setField(request, "objectionReason", "Reason");

        IndividualGrade individualGrade = mock(IndividualGrade.class);
        when(individualGradeRepository.findById(100L)).thenReturn(Optional.of(individualGrade));

        // when
        service.registerObjection(request);

        // then
        verify(gradeObjectionRepository).save(any(GradeObjection.class));
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
