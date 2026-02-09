package org.hit.hradar.domain.evaluation.command.application.scheduler;

import lombok.RequiredArgsConstructor;
import org.hit.hradar.domain.evaluation.command.application.service.CycleStatusService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CycleStatusScheduler {

    private final CycleStatusService cycleStatusService;

    /**
     * 3분마다 상태 자동 갱신
     */
    @Scheduled(fixedRate = 180000)
    public void updateCycleStatus() {
        cycleStatusService.autoUpdateCycleStatus();
    }
}
