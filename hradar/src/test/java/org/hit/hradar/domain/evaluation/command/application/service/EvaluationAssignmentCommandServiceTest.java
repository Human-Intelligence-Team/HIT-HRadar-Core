package org.hit.hradar.domain.evaluation.command.application.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EvaluationAssignmentCommandServiceTest {

    @InjectMocks
    private EvaluationAssignmentCommandService service;

    @Test
    @DisplayName("Load Context")
    void loadContext() {
        assertThat(service).isNotNull();
    }
}
