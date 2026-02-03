package org.hit.hradar.domain.goal.query.service;

import org.hit.hradar.domain.goal.query.dto.response.ContributionBarChartResponse;
import org.hit.hradar.domain.goal.query.dto.response.row.ContributionByTeamGoalRow;
import org.hit.hradar.domain.goal.query.mapper.ContributionMapper;
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
class ContributionQueryServiceTest {

    @InjectMocks
    private ContributionQueryService service;

    @Mock
    private ContributionMapper mapper;

    @Test
    @DisplayName("기여도 차트 조회 - 성공")
    void getMuContributionBarChart_success() {
        // given
        Long empId = 100L;
        ContributionByTeamGoalRow row = new ContributionByTeamGoalRow();
        setField(row, "teamGoalTitle", "Team Goal");
        setField(row, "myContributionPercent", 25.5);

        when(mapper.selectMyContributionByTeamGoals(empId)).thenReturn(List.of(row));

        // when
        ContributionBarChartResponse result = service.getMuContributionBarChart(empId);

        // then
        assertThat(result.getCategories()).contains("Team Goal");
        assertThat(result.getValues()).contains(25.5);
        verify(mapper).selectMyContributionByTeamGoals(empId);
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
