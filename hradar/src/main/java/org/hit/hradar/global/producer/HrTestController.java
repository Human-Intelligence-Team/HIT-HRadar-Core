package org.hit.hradar.global.producer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HrTestController {

    private final HrNotificationProducer producer;

    public HrTestController(HrNotificationProducer producer) {
        this.producer = producer;
    }

    @GetMapping("/test/notify")
    public String notifyTest() {
        producer.sendNotification(1001L, "역량 리포트가 생성되었습니다.");
        return "sent";
    }
}