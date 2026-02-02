package org.hit.hradar.domain.attendance.query.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class IpRangePolicyQueryServiceTest {

    @InjectMocks
    private IpRangePolicyQueryService queryService;

    @Test
    @DisplayName("Load Context")
    void loadContext() {
        assertThat(queryService).isNotNull();
    }
}
