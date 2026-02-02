package org.hit.hradar.domain.grading.command.application.service;

import org.hit.hradar.domain.grading.command.aplication.sevice.IndividualGradeCommandService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class IndividualGradeCommandServiceTest {

    @InjectMocks
    private IndividualGradeCommandService service;

    @Test
    @DisplayName("Load Context")
    void loadContext() {
        assertThat(service).isNotNull();
    }
}
