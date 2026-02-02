package org.hit.hradar.domain.evaluation.query.service;

import org.hit.hradar.domain.evaluation.query.dto.response.CollaborationRadarResponse;
import org.hit.hradar.domain.evaluation.query.dto.response.row.CollaborationRadarRow;
import org.hit.hradar.domain.evaluation.query.mapper.CollaborationRadarMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CollaborationRadarQueryServiceTest {

    @InjectMocks
    private CollaborationRadarQueryService service;

    @Mock
    private CollaborationRadarMapper mapper;

    @Test
    @DisplayName("협업 레이더 차트 조회 - 성공")
    void getMyCollaborationRadar_success() {
        // given
        Long evaluateeId = 1L;
        CollaborationRadarRow row = new CollaborationRadarRow();
        setField(row, "label", "Communication");
        setField(row, "value", 85.0);

        when(mapper.selectMyCollaborationRadar(evaluateeId)).thenReturn(List.of(row));

        // when
        CollaborationRadarResponse result = service.getMyCollaborationRadar(evaluateeId);

        // then
        assertThat(result.getLabels()).contains("Communication");
        assertThat(result.getValues()).contains(85.0);
        verify(mapper).selectMyCollaborationRadar(evaluateeId);
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
